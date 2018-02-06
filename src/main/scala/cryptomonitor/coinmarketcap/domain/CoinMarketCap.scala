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
    price_usd: Option[BigDecimal],
    price_btc: Option[BigDecimal],
    `24h_volume_usd`: Option[BigDecimal],
    market_cap_usd: Option[BigDecimal],
    available_supply: Option[BigDecimal],
    total_supply: Option[BigDecimal],
    max_supply: Option[BigDecimal],
    percent_change_1h: Option[BigDecimal],
    percent_change_24h: Option[BigDecimal],
    percent_change_7d: Option[BigDecimal],
    last_updated: Option[Instant]
)

case class CoinMarketCapApiConfig(uri: String)
