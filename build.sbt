name := "NetServer"

version := "1.2"

scalaVersion := "2.12.1"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature", "-target:jvm-1.8")

libraryDependencies ++= {

  val scalazV = "7.2.10"
  val akkaHttpV = "10.0.5"
  val scalaTestV = "3.2.0-SNAP4"
  val cassandraV = "3.2.0"


  Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV,

    "org.scalaz" %% "scalaz-core" % scalazV,
    "org.scalactic" %% "scalactic" % scalaTestV,
    "org.scalatest" %% "scalatest" % scalaTestV % "test",

    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraV,
    "ch.qos.logback" % "logback-classic" % "1.2.3"
  )
}

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