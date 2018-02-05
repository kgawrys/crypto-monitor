package cryptomonitor.coinmarketcap.domain

import java.time.Instant

sealed trait CoinMarketCapStatus
object CoinMarketCapStatus {
  case class UnexpectedError(error: String)     extends CoinMarketCapStatus
  case class ResponseParsingError(t: Throwable) extends CoinMarketCapStatus
}

case class Tick(
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
    last_updated: Instant
)

case class CoinMarketCapApiConfig(uri: String)
