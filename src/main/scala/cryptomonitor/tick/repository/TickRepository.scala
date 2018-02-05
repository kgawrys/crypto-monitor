package cryptomonitor.tick.repository

import cryptomonitor.coinmarketcap.domain.Tick
import cryptomonitor.core.postgres.PgDriver.api._

class TickRepository {

  val ticks = TableQuery[TickMapping]

  def insert(tick: Tick): DBIOAction[Int, NoStream, Effect.Write] = ticks += tick

  def insertBatch(tickSeq: Seq[Tick]): DBIOAction[Option[Int], NoStream, Effect.Write] = ticks ++= tickSeq

  def getAll: DBIOAction[Seq[Tick], NoStream, Effect.Read] = ticks.result
}
