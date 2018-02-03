import Version._

name := "crypto-monitor"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Dependencies.apiCore

configs(IntegrationTest)

scapegoatVersion in ThisBuild := scapegoatV
scapegoatDisabledInspections := Seq(
  "IncorrectlyNamedExceptions",
  "BigDecimalDoubleConstructor",
  "FinalModifierOnCaseClass"
)
scapegoatMaxErrors   := 0
scapegoatMaxWarnings := 0
scapegoatMaxInfos    := 0