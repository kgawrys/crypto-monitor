package cryptomonitor.tick.service

import cats.data.EitherT
import cats.implicits.catsStdInstancesForFuture
import com.typesafe.scalalogging.StrictLogging
import cryptomonitor.coinmarketcap.domain.{CoinMarketCapStatus, Tick}
import cryptomonitor.tick.repository.TickRepository
import cryptomonitor.coinmarketcap.service.CoinMarketCapApiService
import cryptomonitor.core.postgres.PgDriver.api._

import scala.concurrent.{ExecutionContext, Future}

sealed trait TickUploadResult
case class OkUpload(ticksAdded: Int)       extends TickUploadResult
case object NoRowsAddedOkUpload            extends TickUploadResult
case class FailureUpload(error: Throwable) extends TickUploadResult

class TickUploaderService(
    tickRepository: TickRepository,
    coinMarketCapApiService: CoinMarketCapApiService,
    db: Database
)(implicit ec: ExecutionContext)
    extends StrictLogging {

  def uploadTicks: Future[Either[CoinMarketCapStatus, TickUploadResult]] = {
    logger.info(s"Started uploading ticks")
    (for {
      ticks        <- EitherT(coinMarketCapApiService.getTickerdata)
      insertResult <- EitherT.liftT[Future, CoinMarketCapStatus, TickUploadResult](insertTicks(ticks))
    } yield insertResult).value
  }

  private def insertTicks(ticks: Seq[Tick]): Future[TickUploadResult] = {
    val insertsFut = db.run(tickRepository.insertBatch(ticks))

    insertsFut
      .map {
        case Some(rowsAdded) =>
          logger.info(s"Successfully added $rowsAdded tick rows.")
          OkUpload(rowsAdded)
        case None =>
          logger.error("Successfully finished future, but no tick rows were added")
          NoRowsAddedOkUpload
      }
      .recover {
        case e =>
          val msg = s"Failure during tick row adding"
          logger.error(msg, e)
          FailureUpload(e)
      }
  }
}
