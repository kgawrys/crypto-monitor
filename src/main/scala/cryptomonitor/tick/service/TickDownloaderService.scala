package cryptomonitor.tick.service

import cats.data.EitherT
import cats.implicits.catsStdInstancesForFuture
import com.typesafe.scalalogging.StrictLogging
import cryptomonitor.coinmarketcap.domain.{CoinMarketCapStatus, Tick}
import cryptomonitor.coinmarketcap.service.CoinMarketCapApiService
import cryptomonitor.core.postgres.PgDriver.api._
import cryptomonitor.tick.repository.TickRepository

import scala.concurrent.{ExecutionContext, Future}

sealed trait TickDownloadResult
case class OkDownload(ticksAdded: Int)       extends TickDownloadResult
case object NoRowsAddedOkDownload            extends TickDownloadResult
case class FailureDownload(error: Throwable) extends TickDownloadResult

class TickDownloaderService(
    tickRepository: TickRepository,
    coinMarketCapApiService: CoinMarketCapApiService,
    db: Database
)(implicit ec: ExecutionContext)
    extends StrictLogging {

  def downloadTicks: Future[Either[CoinMarketCapStatus, TickDownloadResult]] = {
    logger.info(s"Started downloading ticks")
    (for {
      ticks        <- EitherT(coinMarketCapApiService.getTickerdata)
      insertResult <- EitherT.liftT[Future, CoinMarketCapStatus, TickDownloadResult](insertTicks(ticks))
    } yield insertResult).value
  }

  private def insertTicks(ticks: Seq[Tick]): Future[TickDownloadResult] = {
    val insertsFut = db.run(tickRepository.insertBatch(ticks))

    insertsFut
      .map {
        case Some(rowsAdded) =>
          logger.info(s"Successfully added $rowsAdded tick rows.")
          OkDownload(rowsAdded)
        case None =>
          logger.error("Successfully finished future, but no tick rows were added")
          NoRowsAddedOkDownload
      }
      .recover {
        case e =>
          val msg = "Failure during tick row adding"
          logger.error(msg, e)
          FailureDownload(e)
      }
  }
}
