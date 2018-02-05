package cryptomonitor.common

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.testkit._
import com.typesafe.config.ConfigFactory
import cryptomonitor.core.Setup
import cryptomonitor.core.json.CryptoMonitorJsonSupport
import slick.jdbc.PostgresProfile.api._
import org.scalatest._

import scala.concurrent.duration._

trait IntegrationTestModule extends Setup with CryptoMonitorJsonSupport with ScalatestRouteTest with BeforeAndAfterAll with Matchers {
  self: Suite =>

  override lazy val config = ConfigFactory.load()
  override lazy val db     = Database.forConfig("postgres", config)

  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(1.minute dilated system)

  lazy val sealedRoutes: Route = Route.seal(routes)

  override protected def afterAll(): Unit = {
    cleanup()
  }

  def cleanup(): Unit = {
    db.close()
  }

}
