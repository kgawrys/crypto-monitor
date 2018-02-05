package cryptomonitor.tick.service

import akka.actor.Actor

case object Fire

class UploadActor extends Actor {
  def receive = {
    case Fire => println("uploading...,")
    case _    => println("huh?")
  }
}
