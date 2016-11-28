name := "NetServer"

version := "1.0"

scalaVersion := "2.12.0"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature", "-target:jvm-1.8")

packageName in Docker := "carmonit-net-server"

dockerExposedPorts := Seq(5000)

resolvers += "Artima plugins repository" at "http://repo.artima.com/releases"

libraryDependencies ++= {
  val akkaV = "2.4.14"
  val akkaHttpV = "10.0.0"
  val scalaTestV = "3.0.1"
  val scalazVersion = "7.2.7"

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

    "ch.qos.logback" % "logback-classic" % "1.1.7"

    //WAITING LIST
    //"com.github.swagger-akka-http" %% "swagger-akka-http" % "0.7.3",
  )
}
unmanagedResourceDirectories in Compile += {
  baseDirectory.value / "src/main/resources"
}

licenses += ("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php"))

// enable updating file headers //
import com.typesafe.sbt.SbtScalariform
import de.heikoseeberger.sbtheader.license.Apache2_0

headers := Map(
  "scala" -> Apache2_0("2016", "Dmytro Rashko"),
  "conf" -> Apache2_0("2016", "Dmytro Rashko", "#")
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
  mainClass in reStart := Some("com.carmonit.server.ServerRunner")
)

// enable plugins //
enablePlugins(AutomateHeaderPlugin)