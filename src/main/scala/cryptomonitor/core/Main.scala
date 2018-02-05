package cryptomonitor.core

import akka.actor.{ActorSystem, Props}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import com.typesafe.config.ConfigFactory
import cryptomonitor.coinmarketcap.domain.CoinMarketCapApiConfig
import cryptomonitor.coinmarketcap.service.CoinMarketCapApiService
import cryptomonitor.health.routers.HealthCheckRouter
import cryptomonitor.tick.repository.TickRepository
import cryptomonitor.tick.service.{Fire, TickUploaderService, UploadActor}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext

case class ServerConfig(interface: String, port: Int, hostname: String)

trait Setup {
  import com.softwaremill.macwire._

  implicit val system: ActorSystem
  implicit def executor: ExecutionContext
  implicit val materializer: Materializer

  lazy val logger = Logging(system, getClass)
  lazy val config = ConfigFactory.load()

  lazy val db = Database.forConfig("postgres", config)

  lazy val coinMarketCapApiConfig: CoinMarketCapApiConfig = CoinMarketCapApiConfig(
    uri = config.getString("coinMarketCap.api.allTickerUri")
  )

  lazy val serverConfig = ServerConfig(
    interface = config.getString("http.interface"),
    port      = config.getInt("http.port"),
    hostname  = config.getString("http.hostname")
  )

  lazy val fireUploadActor = system.actorOf(Props[UploadActor])

  lazy val tickRepository: TickRepository = wire[TickRepository]

  lazy val coinMarketCapApiService: CoinMarketCapApiService = wire[CoinMarketCapApiService]
  lazy val tickUploaderService: TickUploaderService         = wire[TickUploaderService]

  lazy val healthCheckRouter: HealthCheckRouter = wire[HealthCheckRouter]

  lazy val routes = logRequestResult("crypto-monitor") {
    healthCheckRouter.routes
  }

}

object Main extends App with Setup {
  implicit val system       = ActorSystem()
  implicit val executor     = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val scheduler = QuartzSchedulerExtension(system)
  QuartzSchedulerExtension(system).schedule("Every5Seconds", fireUploadActor, Fire)

  Http()
    .bindAndHandle(routes, serverConfig.interface, serverConfig.port)
    .map { binding =>
      logger.info(s"HTTP server started at ${binding.localAddress}")
    }
    .recover { case ex => logger.error(ex, "Could not start HTTP server") }
}
