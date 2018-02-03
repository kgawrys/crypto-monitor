package cryptomonitor.health.routers

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

class HealthCheckRouter {

  val routes: Route = {
    (get & path("health") & pathEndOrSingleSlash) {
      complete(StatusCodes.OK)
    }
  }
}
