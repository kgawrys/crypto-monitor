package cryptomonitor.coinmarketcap.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging
import cryptomonitor.coinmarketcap.service.CoinmarketcapStatus.{ResponseParsingError, UnexpectedError}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

sealed trait CoinmarketcapStatus
object CoinmarketcapStatus {
  case class UnexpectedError(error: String)     extends CoinmarketcapStatus
  case class ResponseParsingError(t: Throwable) extends CoinmarketcapStatus
}

case class TickerData(
    id: String,
    name: String,
    symbol: String,
    rank: Int,
    price_usd: Double,
    price_btc: Double,
    `24h_volume_usd`: Double,
    market_cap_usd: Option[Double],
    available_supply: Option[Double],
    total_supply: Option[Double],
    max_supply: Option[Double],
    percent_change_1h: Float,
    percent_change_24h: Float,
    percent_change_7d: Float,
    last_updated: Int
)

case class CoinmarketcapApiConfig(uri: String)

class CoinmarketcapApiService(coinmarketcapApiConfig: CoinmarketcapApiConfig)(implicit as: ActorSystem,
                                                                              mat: Materializer,
                                                                              ec: ExecutionContext)
    extends FailFastCirceSupport
    with StrictLogging {

  val allTickers     = "https://api.coinmarketcap.com/v1/ticker/?limit=0"
  val first10Tickers = "https://api.coinmarketcap.com/v1/ticker/?limit=10"

  // todo from conf
  def getCoinmarketcapUrl: String = {
//    s"${config.npmUri.uri}/$urlRepresentation"
    coinmarketcapApiConfig.uri
  }

  def getAllTickerdata: Future[Either[CoinmarketcapStatus, Seq[TickerData]]] = {
    val request = createRequest(Uri(getCoinmarketcapUrl))
    logger.trace("Coinmarketcap library request sent {} ", request)
    sendRequest(request)
  }

  private def createRequest(uri: Uri) = RequestBuilding.Get(uri)

  private def sendRequest(request: HttpRequest) = {
    Http().singleRequest(request).flatMap { response =>
      response.status match {
        case OK =>
          Unmarshal(response.entity)
            .to[Seq[TickerData]]
            .map(Right(_))
            .recover {
              case NonFatal(e) =>
                logger.warn(s"Failed unmarshalling response for request ${request.uri}", e)
                Left(ResponseParsingError(e))
            }
        case _ =>
          logMsg(request, response)
          Future.successful(Left(UnexpectedError("Unable to connect to CoinmarketCap")))
      }
    }
  }

  private def logMsg(request: HttpRequest, response: HttpResponse): Unit = {
    response.entity.toStrict(5.seconds).foreach { entity =>
      val body = entity.data.decodeString("UTF-8")
      logger.error(errorMessage(request, response, body))
    }
  }

  private def errorMessage(request: HttpRequest, response: HttpResponse, body: String) = {
    s"Request to Coinmarketcap $request failed because ${response.status} and entity $body"
  }
}
