import sbt._

object Version {
  val akkaV          = "2.4.20"
  val akkaHttpV      = "10.0.11"
  val akkaHttpCirceV = "1.19.0"
  val catsV          = "0.9.0"
  val circeV         = "0.9.1"
  val commonsV       = "3.4"
  val enumeratumV    = "1.5.12"
  val logbackV       = "1.2.3"
  val macwireV       = "2.3.0"
  val mockitoV       = "1.10.19"
  val pgDriverV      = "42.2.1"
  val scalaLoggingV  = "3.7.2"
  val scalaTestV     = "3.0.5"
  val scapegoatV     = "1.3.4"
  val slickV         = "3.2.1"
  val slickPgV       = "0.15.7"

}

object Library {
  import Version._

  val akkaSfl4j = "com.typesafe.akka" %% "akka-slf4j" % akkaV

  // http
  val akkaHttpCore    = "com.typesafe.akka" %% "akka-http-core"    % akkaHttpV
  val akkaHttp        = "com.typesafe.akka" %% "akka-http"         % akkaHttpV
  val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV
  val akkaHttpCirce   = "de.heikoseeberger" %% "akka-http-circe"   % akkaHttpCirceV

  // circe
  val circe        = "io.circe" %% "circe-core"    % circeV
  val circeGeneric = "io.circe" %% "circe-generic" % circeV
  val circeParser  = "io.circe" %% "circe-parser"  % circeV
  val circeJava8   = "io.circe" %% "circe-java8"   % circeV

  val circeBundle = Seq(circe, circeGeneric, circeParser, circeJava8)

  // macwire
  val macwireMacros = "com.softwaremill.macwire" %% "macros" % macwireV
  val macwireUtil   = "com.softwaremill.macwire" %% "util"   % macwireV

  // slick
  val postgres    = "org.postgresql"      % "postgresql"      % pgDriverV
  val slick       = "com.typesafe.slick"  %% "slick"          % slickV
  val slickHikari = "com.typesafe.slick"  %% "slick-hikaricp" % slickV
  val slickPg     = "com.github.tminglei" %% "slick-pg"       % slickPgV

  // others
  val cats            = "org.typelevel"              %% "cats"             % catsV
  val enumeratum      = "com.beachape"               %% "enumeratum"       % enumeratumV
  val enumeratumCirce = "com.beachape"               %% "enumeratum-circe" % enumeratumV
  val mockito         = "org.mockito"                % "mockito-all"       % mockitoV % "test,it"
  val logback         = "ch.qos.logback"             % "logback-classic"   % logbackV
  val scalaLogging    = "com.typesafe.scala-logging" %% "scala-logging"    % scalaLoggingV
  val scalatest       = "org.scalatest"              %% "scalatest"        % scalaTestV
}

object Dependencies {
  import Library._

  val cryptomonitor = Seq(
    akkaHttp,
    akkaHttpCirce,
    akkaHttpCore,
    akkaHttpTestkit % "test,it",
    akkaSfl4j,
    logback,
    macwireMacros % "provided",
    macwireUtil,
    mockito,
    scalatest % "test,it",
    postgres,
    scalaLogging,
    slick,
    slickHikari,
    slickPg
  ) ++ circeBundle
}
