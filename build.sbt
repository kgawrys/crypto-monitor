import Version._

name := "crypto-monitor"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Dependencies.cryptomonitor

configs(IntegrationTest)

Seq(Defaults.itSettings: _*)

scapegoatVersion in ThisBuild := scapegoatV
scapegoatDisabledInspections := Seq(
  "IncorrectlyNamedExceptions",
  "BigDecimalDoubleConstructor",
  "FinalModifierOnCaseClass"
)
scapegoatMaxErrors   := 0
scapegoatMaxWarnings := 0
scapegoatMaxInfos    := 0

PostgresMigrations.settings

testOptions in IntegrationTest += Tests.Argument(TestFrameworks.ScalaTest, "-W", "10", "20")
logBuffered in IntegrationTest := false
