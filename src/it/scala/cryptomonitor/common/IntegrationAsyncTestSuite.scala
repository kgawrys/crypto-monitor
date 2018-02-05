package cryptomonitor.common

import org.scalatest.{AsyncFlatSpec, EitherValues, OptionValues}

trait IntegrationAsyncTestSuite extends AsyncFlatSpec with OptionValues with EitherValues
