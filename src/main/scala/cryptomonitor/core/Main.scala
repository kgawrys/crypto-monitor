package cryptomonitor.core

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.ConfigFactory
import cryptomonitor.coinmarketcap.domain.CoinMarketCapApiConfig
import cryptomonitor.coinmarketcap.service.CoinMarketCapApiService
import cryptomonitor.health.routers.HealthCheckRouter

import scala.concurrent.ExecutionContext

case class ServerConfig(interface: String, port: Int, hostname: String)

trait Setup {
  import com.softwaremill.macwire._

  implicit val system: ActorSystem
  implicit def executor: ExecutionContext
  implicit val materializer: Materializer

  lazy val logger = Logging(system, getClass)
  lazy val config = ConfigFactory.load()

  lazy val coinMarketCapApiConfig: CoinMarketCapApiConfig = CoinMarketCapApiConfig(
    uri = config.getString("coinMarketCap.api.allTickerUri")
  )

  lazy val serverConfig = ServerConfig(
    interface = config.getString("http.interface"),
    port      = config.getInt("http.port"),
    hostname  = config.getString("http.hostname")
  )

  lazy val coinMarketCapApiService: CoinMarketCapApiService = wire[CoinMarketCapApiService]

  lazy val healthCheckRouter: HealthCheckRouter = wire[HealthCheckRouter]

  lazy val routes = logRequestResult("crypto-monitor") {
    healthCheckRouter.routes
  }

}

object Main extends App with Setup {
  implicit val system       = ActorSystem()
  implicit val executor     = system.dispatcher
  implicit val materializer = ActorMaterializer()

  Http()
    .bindAndHandle(routes, serverConfig.interface, serverConfig.port)
    .map { binding =>
      logger.info(s"HTTP server started at ${binding.localAddress}")
    }
    .recover { case ex => logger.error(ex, "Could not start HTTP server") }
}
