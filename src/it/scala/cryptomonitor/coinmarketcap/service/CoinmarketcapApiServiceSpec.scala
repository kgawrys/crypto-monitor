package cryptomonitor.coinmarketcap.service

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFlatSpec, Matchers}

class CoinmarketcapApiServiceSpec extends AsyncFlatSpec with Matchers with MockitoSugar with ScalatestRouteTest {

  val first10Tickers = "https://api.coinmarketcap.com/v1/ticker/?limit=10"

  val config = CoinmarketcapApiConfig(first10Tickers)

  val coinmarketcapApiService = new CoinmarketcapApiService(config)

  it should "build npm metadata url" in {
    for {
      result <- coinmarketcapApiService.getAllTickerdata
    } yield {
      println(result.right.get)
      result.right.get shouldBe a[Seq[TickerData]]
    }
  }

}
