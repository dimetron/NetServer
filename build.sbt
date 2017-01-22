name := "NetServer"

version := "1.1"

scalaVersion := "2.12.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature", "-target:jvm-1.8")

packageName in Docker := "carmonit-net-server"

dockerExposedPorts := Seq(5000)

resolvers += "Artima plugins repository" at "http://repo.artima.com/releases"

libraryDependencies ++= {

  val akkaHttpV = "10.0.1"
  val scalaTestV = "3.2.0-SNAP1"
  val scalazVersion = "7.2.8"

  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV,

    "org.scalaz" %% "scalaz-core" % scalazVersion,
    "org.scalactic" %% "scalactic" % scalaTestV,
    "org.scalatest" %% "scalatest" % scalaTestV % "test",

    "ch.qos.logback" % "logback-classic" % "1.1.8",

    // https://github.com/swagger-akka-http/swagger-akka-http
    "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.9.1"
  )
}
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

// enable plugins //
enablePlugins(AutomateHeaderPlugin)