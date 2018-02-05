package cryptomonitor.coinmarketcap.service

import cryptomonitor.coinmarketcap.domain.{CoinMarketCapApiConfig, Tick}
import cryptomonitor.common.{IntegrationAsyncTestSuite, IntegrationTestModule}

class CoinMarketCapApiServiceSpec extends IntegrationAsyncTestSuite with IntegrationTestModule {

  val allTickers     = "https://api.coinmarketcap.com/v1/ticker/?limit=0"
  val first10Tickers = "https://api.coinmarketcap.com/v1/ticker/?limit=10"

  override lazy val coinMarketCapApiConfig = CoinMarketCapApiConfig(first10Tickers)

  it should "build tick response" in {
    for {
      result <- coinMarketCapApiService.getAllTickerdata
    } yield {
      val results = result.right.get
      results.head shouldBe a[Tick]
      results.size shouldBe 10

    }
  }

}
