package cryptomonitor.coinmarketcap.repository

import java.time.Instant

import cryptomonitor.coinmarketcap.domain.Tick
import cryptomonitor.core.postgres.PgDriver.api._
import slick.lifted.{ProvenShape, Tag}

class TickMapping(tag: Tag) extends Table[Tick](tag, "ticks") {

  def id: Rep[String]                      = column("id")
  def name: Rep[String]                    = column("reason")
  def symbol: Rep[String]                  = column("symbol")
  def rank: Rep[Int]                       = column("rank")
  def priceUsd: Rep[Double]                = column("price_usd")
  def priceBtc: Rep[Double]                = column("price_btc")
  def volumeUsd24h: Rep[Double]            = column("volume_usd_24h")
  def marketCapUsd: Rep[Option[Double]]    = column("market_cap_usd")
  def availableSupply: Rep[Option[Double]] = column("available_supply")
  def totalSupply: Rep[Option[Double]]     = column("total_supply")
  def maxSupply: Rep[Option[Double]]       = column("max_supply")
  def percentChange1h: Rep[Float]          = column("percent_change_1h")
  def percentChange24h: Rep[Float]         = column("percent_change_24h")
  def percentChange7d: Rep[Float]          = column("percent_change_7d")
  def lastUpdated: Rep[Instant]            = column("last_updated")

  override def * : ProvenShape[Tick] =
    (id,
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
