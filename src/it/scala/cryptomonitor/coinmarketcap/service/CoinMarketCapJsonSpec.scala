package cryptomonitor.coinmarketcap.service

import cryptomonitor.coinmarketcap.domain.Tick
import cryptomonitor.core.json.CryptoMonitorJsonSupport
import io.circe.generic.auto._
import io.circe.parser._
import org.scalatest.{EitherValues, FlatSpec, Matchers}

class CoinMarketCapJsonSpec extends FlatSpec with Matchers with EitherValues with CryptoMonitorJsonSupport {

  it should "correctly parse coin market cap json" in {
    val json =
      """
        |[
        |    {
        |        "id": "bitcoin",
        |        "name": "Bitcoin",
        |        "symbol": "BTC",
        |        "rank": "1",
        |        "price_usd": "8865.79",
        |        "price_btc": "1.0",
        |        "24h_volume_usd": "7004690000.0",
        |        "market_cap_usd": "149344117295",
        |        "available_supply": "16844987.0",
        |        "total_supply": "16844987.0",
        |        "max_supply": "21000000.0",
        |        "percent_change_1h": "1.53",
        |        "percent_change_24h": "-3.88",
        |        "percent_change_7d": "-24.72",
        |        "last_updated": "1517754269"
        |    },
        |    {
        |        "id": "ethereum",
        |        "name": "Ethereum",
        |        "symbol": "ETH",
        |        "rank": "2",
        |        "price_usd": "806.329",
        |        "price_btc": "0.102107",
        |        "24h_volume_usd": "3095400000.0",
        |        "market_cap_usd": "78555734433.0",
        |        "available_supply": "97423923.0",
        |        "total_supply": "97423923.0",
        |        "max_supply": null,
        |        "percent_change_1h": "-1.43",
        |        "percent_change_24h": "-12.01",
        |        "percent_change_7d": "-32.78",
        |        "last_updated": "1517825954"
        |    }
        |]
      """.stripMargin

    val parseResult = parse(json)

    val ticks = parseResult.right.get.as[Seq[Tick]].getOrElse(Nil)
    ticks.size shouldEqual 2
  }
}
