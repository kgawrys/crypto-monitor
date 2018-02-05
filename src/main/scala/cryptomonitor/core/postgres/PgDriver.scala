package cryptomonitor.core.postgres

import com.github.tminglei.slickpg._
import slick.jdbc.JdbcCapabilities

/**
  * Configuration of the Postgres Driver
  */
trait PgDriver extends ExPostgresProfile with PgDate2Support with PgArraySupport with PgEnumSupport {

  def pgjson = "jsonb"

  override protected def computeCapabilities =
    super.computeCapabilities + JdbcCapabilities.insertOrUpdate

  override val api = MyAPI

  object MyAPI extends API with ArrayImplicits with DateTimeImplicits
}

object PgDriver extends PgDriver
