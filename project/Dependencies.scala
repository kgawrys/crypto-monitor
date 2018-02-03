import sbt._

object Version {
  val akkaV      = "2.4.16"
  val akkaHttpV  = "10.0.1"
  val logbackV   = "1.1.8"
  val macwireV   = "2.2.5"
  val scalaTestV = "3.0.1"
  val commonsV   = "3.4"
}

object Library {
  import Version._

  // akka
  val akkaActor       = "com.typesafe.akka" %% "akka-actor"       % akkaV
  val akkaSfl4j       = "com.typesafe.akka" %% "akka-slf4j"       % akkaV
  val akkaStream      = "com.typesafe.akka" %% "akka-stream"      % akkaV
  val akkaTestkit     = "com.typesafe.akka" %% "akka-testkit"     % akkaV
  val akkaPersistence = "com.typesafe.akka" %% "akka-persistence" % akkaV

  // http
  val akkaHttpCore      = "com.typesafe.akka" %% "akka-http-core"       % akkaHttpV
  val akkaHttp          = "com.typesafe.akka" %% "akka-http"            % akkaHttpV
  val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV
  val akkaHttpTestkit   = "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpV

  // macwire
  val macwireMacros = "com.softwaremill.macwire" %% "macros" % macwireV
  val macwireUtil   = "com.softwaremill.macwire" %% "util"   % macwireV

  // others
  val logback   = "ch.qos.logback" % "logback-classic" % logbackV
  val scalatest = "org.scalatest"  %% "scalatest"      % scalaTestV

}

object Dependencies {
  import Library._

  val apiCore = Seq(
    akkaActor,
    akkaSfl4j,
    akkaStream,
    akkaTestkit,
    akkaHttpCore,
    akkaHttp,
    akkaHttpSprayJson,
    akkaHttpTestkit,
    akkaPersistence,
    akkaHttpTestkit % "test,it",
    scalatest       % "test,it",
    logback,
    scalatest,
    macwireMacros % "provided",
    macwireUtil
  )
}
