name := "NetServer"

version := "1.2"

scalaVersion := "2.12.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature", "-target:jvm-1.8")

libraryDependencies ++= {

  val scalazV = "7.2.10"

  val akkaVersion = "2.4.17"
  val akkaHttpV = "10.0.5"
  val scalaTestV = "3.2.0-SNAP4"
  val cassandraV = "3.1.0"

  val gatlingVersion = "3.0.0-M7.FL"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",

    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV,

    //integration alpakka
    "com.lightbend.akka" %% "akka-stream-alpakka-cassandra" % "0.7",

    "org.scalaz" %% "scalaz-core" % scalazV,

    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraV,
    "ch.qos.logback" % "logback-classic" % "1.2.3",

    "org.scalactic" %% "scalactic" % scalaTestV % "test",
    "org.scalatest" %% "scalatest" % scalaTestV % "test",

    //integration load tests with gatling
    "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % "it",
    "io.gatling"            % "gatling-test-framework"    % gatlingVersion % "it"
  )
}

//default log level for sbt
logLevel := Level.Error

// sbt dependencyUpdatesReport
unmanagedResourceDirectories in Compile += {
  baseDirectory.value / "src/main/resources"
}

// don't ignore Suites which is the default for the junit-interface
testOptions += Tests.Argument(TestFrameworks.JUnit, "--ignore-runners=")

licenses += ("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php"))

// enable updating file headers //
import com.typesafe.sbt.SbtScalariform
import de.heikoseeberger.sbtheader.license.Apache2_0

headers := Map(
  "scala" -> Apache2_0("2017", "Dmytro Rashko"),
  "conf" -> Apache2_0("2017", "Dmytro Rashko", "#")
)

// enable scala code formatting //
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform

// Scalariform settings
SbtScalariform.autoImport.scalariformPreferences := SbtScalariform.autoImport.scalariformPreferences.value
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 150)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(CompactControlReadability, true)

// enable sbt-revolver
Revolver.settings ++ Seq(
  Revolver.enableDebugging(port = 5050, suspend = false),
  mainClass in reStart := Some("com.carmonit.server.ServerMain")
)

//gatling
val gatlingRepository = "http://repository.gatling.io/ce2dec95-b42a-4518-9371-ad5672b320ad/content/repositories/releases"
resolvers += "Gatling Corp's Repository" at gatlingRepository

enablePlugins(GatlingPlugin)
javaOptions in Gatling := overrideDefaultJavaOptions("-Xms1024m", "-Xmx1024m")
logLevel in Gatling := Level.Error

// enable plugins //
enablePlugins(AutomateHeaderPlugin)

// docker plugin
enablePlugins(JavaServerAppPackaging)

dockerBaseImage       := "openjdk"
dockerRepository      := Some("dimetron")
dockerExposedPorts    := Vector(8080, 8888)

version.in(Docker)    := "latest"
packageName in Docker := "netserver"
daemonUser.in(Docker) := "root"
maintainer.in(Docker) := "Dmytro Rashko"

mappings in Universal += {
  val conf = (resourceDirectory in Compile).value / "application.conf"
  conf -> "application.conf"
}

javaOptions in Universal ++= Seq(
  // -J params will be added as jvm parameters
  "-J-Xmx64m",
  "-J-Xms64m"

  // others will be added as app parameters
  //"-Dproperty=true",
  //"-port=8080",

  // you can access any build setting/task here
  //s"-version=${version.value}"
)



