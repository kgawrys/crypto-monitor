package cryptomonitor.coinmarketcap.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging
import cryptomonitor.coinmarketcap.domain.CoinMarketCapStatus.{ResponseParsingError, UnexpectedError}
import cryptomonitor.coinmarketcap.domain.{CoinMarketCapApiConfig, CoinMarketCapStatus, Tick}
import cryptomonitor.core.json.CryptoMonitorJsonSupport
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class CoinMarketCapApiService(coinmarketcapApiConfig: CoinMarketCapApiConfig)(implicit as: ActorSystem,
                                                                              mat: Materializer,
                                                                              ec: ExecutionContext)
    extends FailFastCirceSupport
    with CryptoMonitorJsonSupport
    with StrictLogging {

  def getTickerdata: Future[Either[CoinMarketCapStatus, Seq[Tick]]] = {
    val request = createRequest(Uri(coinmarketcapApiConfig.uri))
    logger.debug("CoinMarketCap library request sent {} ", request)
    sendRequest(request)
  }

  private def createRequest(uri: Uri) = RequestBuilding.Get(uri)

  private def sendRequest(request: HttpRequest) = {
    Http().singleRequest(request).flatMap { response =>
      response.status match {
        case OK =>
          Unmarshal(response.entity)
            .to[Seq[Tick]]
            .map(Right(_))
            .recover {
              case NonFatal(e) =>
                logger.warn(s"Failed unmarshalling response for request ${request.uri}", e)
                Left(ResponseParsingError(e))
            }
        case _ =>
          logMsg(request, response)
          Future.successful(Left(UnexpectedError("Unable to connect to CoinMarketCap")))
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
    s"Request to CoinMarketCap $request failed because ${response.status} and entity $body"
  }
}
