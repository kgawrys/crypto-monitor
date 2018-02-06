package cryptomonitor.tick.service

import akka.actor.Actor
import com.typesafe.scalalogging.StrictLogging

case object Fire

class TickDownloadActor(tickDownloaderService: TickDownloaderService) extends Actor with StrictLogging {
  def receive = {
    case Fire => tickDownloaderService.downloadTicks
    case _    => logger.error("Unknown message")
  }
}
