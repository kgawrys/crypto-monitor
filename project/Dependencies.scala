import sbt._

object Version {
  val akkaV         = "2.4.20"
  val akkaHttpV     = "10.0.11"
  val akkaHttpCirce = "1.15.0"
  val cats          = "0.9.0"
  val circe         = "0.7.1"
  val commonsV      = "3.4"
  val enumeratum    = "1.5.12"
  val logbackV      = "1.2.3"
  val macwireV      = "2.3.0"
  val pgDriverV     = "42.1.1"
  val scalaTestV    = "3.0.5"
  val scapegoatV    = "1.3.3"
  val slickV        = "3.2.1"
  val slickPgV      = "0.15.3"

}

object Library {
  import Version._

  val akkaSfl4j = "com.typesafe.akka" %% "akka-slf4j" % akkaV

  // http
  val akkaHttpCore    = "com.typesafe.akka" %% "akka-http-core"    % akkaHttpV
  val akkaHttp        = "com.typesafe.akka" %% "akka-http"         % akkaHttpV
  val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV

  // circe
  val circeBundle = Seq(
    "io.circe" %% "circe-core"    % Version.circe,
    "io.circe" %% "circe-generic" % Version.circe,
    "io.circe" %% "circe-parser"  % Version.circe,
    "io.circe" %% "circe-java8"   % Version.circe
  )

  // macwire
  val macwireMacros = "com.softwaremill.macwire" %% "macros" % macwireV
  val macwireUtil   = "com.softwaremill.macwire" %% "util"   % macwireV

  // slick
  val postgres    = "org.postgresql"      % "postgresql"      % Version.pgDriverV
  val slick       = "com.typesafe.slick"  %% "slick"          % slickV
  val slickHikari = "com.typesafe.slick"  %% "slick-hikaricp" % slickV
  val slickPg     = "com.github.tminglei" %% "slick-pg"       % slickPgV

  // others
  val cats            = "org.typelevel"  %% "cats"             % Version.cats
  val enumeratum      = "com.beachape"   %% "enumeratum"       % Version.enumeratum
  val enumeratumCirce = "com.beachape"   %% "enumeratum-circe" % Version.enumeratum
  val logback         = "ch.qos.logback" % "logback-classic"   % logbackV
  val scalatest       = "org.scalatest"  %% "scalatest"        % scalaTestV
}

object Dependencies {
  import Library._

  val cryptomonitor = Seq(
    akkaHttpCore,
    akkaHttp,
    akkaHttpTestkit % "test,it",
    akkaSfl4j,
    logback,
    macwireMacros % "provided",
    macwireUtil,
    scalatest % "test,it",
    postgres,
    slick,
    slickHikari,
    slickPg
  )
}
