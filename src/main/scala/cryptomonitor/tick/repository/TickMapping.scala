package cryptomonitor.tick.repository

import java.time.Instant

import cryptomonitor.coinmarketcap.domain.Tick
import cryptomonitor.core.postgres.PgDriver.api._
import slick.lifted.{ProvenShape, Tag}

class TickMapping(tag: Tag) extends Table[Tick](tag, "ticks") {

  def coinMarketCapId: Rep[String]             = column("coin_market_cap_id")
  def name: Rep[String]                        = column("name")
  def symbol: Rep[String]                      = column("symbol")
  def rank: Rep[Int]                           = column("rank")
  def priceUsd: Rep[BigDecimal]                = column("price_usd")
  def priceBtc: Rep[BigDecimal]                = column("price_btc")
  def volumeUsd24h: Rep[BigDecimal]            = column("volume_usd_24h")
  def marketCapUsd: Rep[Option[BigDecimal]]    = column("market_cap_usd")
  def availableSupply: Rep[Option[BigDecimal]] = column("available_supply")
  def totalSupply: Rep[Option[BigDecimal]]     = column("total_supply")
  def maxSupply: Rep[Option[BigDecimal]]       = column("max_supply")
  def percentChange1h: Rep[BigDecimal]         = column("percent_change_1h")
  def percentChange24h: Rep[BigDecimal]        = column("percent_change_24h")
  def percentChange7d: Rep[BigDecimal]         = column("percent_change_7d")
  def lastUpdated: Rep[Instant]                = column("last_updated")
  def capturedAt: Rep[Instant]                 = column("captured_at")

  override def * : ProvenShape[Tick] =
    (coinMarketCapId,
     name,
     symbol,
     rank,
     priceUsd,
     priceBtc,
     volumeUsd24h,
     marketCapUsd,
     availableSupply,
     totalSupply,
     maxSupply,
     percentChange1h,
     percentChange24h,
     percentChange7d,
     lastUpdated) <> (Tick.tupled, Tick.unapply)
}
