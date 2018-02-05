package cryptomonitor.coinmarketcap.service

import akka.http.scaladsl.testkit.ScalatestRouteTest
import cryptomonitor.coinmarketcap.domain.{CoinMarketCapApiConfig, Tick}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFlatSpec, Matchers}

class CoinMarketCapApiServiceSpec extends AsyncFlatSpec with Matchers with MockitoSugar with ScalatestRouteTest {

  val allTickers     = "https://api.coinmarketcap.com/v1/ticker/?limit=0"
  val first10Tickers = "https://api.coinmarketcap.com/v1/ticker/?limit=10"

  val config = CoinMarketCapApiConfig(first10Tickers)

  val coinMarketCapApiService = new CoinMarketCapApiService(config)

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
