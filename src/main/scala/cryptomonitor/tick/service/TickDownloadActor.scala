package cryptomonitor.tick.service

import akka.actor.Actor

case object Fire

class TickDownloadActor extends Actor {
  def receive = {
    case Fire => println("downloading...,")
    case _    => println("huh?")
  }
}
