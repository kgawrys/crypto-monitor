package cryptomonitor.tick.service

import cryptomonitor.coinmarketcap.domain.CoinMarketCapApiConfig
import cryptomonitor.common.{IntegrationAsyncTestSuite, IntegrationTestModule}

class TickDownloaderServiceSpec extends IntegrationAsyncTestSuite with IntegrationTestModule {

  val first10Tickers = "https://api.coinmarketcap.com/v1/ticker/?limit=10"

  override lazy val coinMarketCapApiConfig = CoinMarketCapApiConfig(first10Tickers)

  it should "correctly add ticks to database" in {
    for {
      _     <- tickDownloaderService.downloadTicks
      ticks <- db.run(tickRepository.getAll)
    } yield {
      ticks.size shouldEqual 10
    }

  }

}
