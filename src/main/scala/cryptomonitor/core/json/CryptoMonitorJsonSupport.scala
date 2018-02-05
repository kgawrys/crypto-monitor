package cryptomonitor.core.json

import java.time.Instant
import cats.syntax.either._
import io.circe.{Decoder, Encoder}

trait CryptoMonitorJsonSupport {

  implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)

  implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emap { str =>
    Either.catchNonFatal(Instant.ofEpochSecond(str.toLong)).leftMap(t => "Instant")
  }
}
