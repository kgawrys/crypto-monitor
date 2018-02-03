logLevel := Level.Warn

addSbtPlugin("com.typesafe.sbt"       % "sbt-native-packager"    % "1.3.3")
addSbtPlugin("com.timushev.sbt"       % "sbt-updates"            % "0.3.4")
addSbtPlugin("com.geirsson"           % "sbt-scalafmt"           % "1.4.0")
addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat"         % "1.0.7")
addSbtPlugin("io.spray"               % "sbt-revolver"           % "0.9.1")
addSbtPlugin("org.scalastyle"         %% "scalastyle-sbt-plugin" % "1.0.0")

addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.2.0")
resolvers += "Flyway" at "https://flywaydb.org/repo"