name := "NetServer"

version := "1.4"

scalaVersion := "2.12.3"

// Scalac flags http://tpolecat.github.io/2017/04/25/scalac-flags.html
scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding", "utf-8", // Specify character encoding used by source files.
  "-explaintypes", // Explain type errors in more detail.
  "-feature", // Emit warning and location for usages of features that should be imported explicitly.
  "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
  "-language:experimental.macros", // Allow macro definition (besides implementation and application)
  "-language:higherKinds", // Allow higher-kinded types
  "-language:implicitConversions", // Allow definition of implicit functions called views
  "-unchecked", // Enable additional warnings where generated code depends on assumptions.
  "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xfuture", // Turn on future language features.
  "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
  "-Xlint:by-name-right-associative", // By-name parameter of right associative operator.
  "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
  "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
  "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
  "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
  "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
  "-Xlint:nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
  "-Xlint:option-implicit", // Option.apply used implicit view.
  "-Xlint:package-object-classes", // Class or object defined in package object.
  "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
  "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
  "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
  "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
  "-Xlint:unsound-match", // Pattern match may not be typesafe.
  "-Yno-adapted-args", // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
  "-Ypartial-unification", // Enable partial unification in type constructor inference
  "-Ywarn-dead-code", // Warn when dead code is identified.
  "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
  "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
  "-Ywarn-infer-any", // Warn when a type argument is inferred to be `Any`.
  "-Ywarn-nullary-override", // Warn when non-nullary `def f()' overrides nullary `def f'.
  "-Ywarn-nullary-unit", // Warn when nullary methods return Unit.
  "-Ywarn-numeric-widen", // Warn when numerics are widened.
  "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
  "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
  "-Ywarn-unused:locals", // Warn if a local definition is unused.
  "-Ywarn-unused:params", // Warn if a value parameter is unused.
  "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
  "-Ywarn-unused:privates", // Warn if a private member is unused.
  "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
)

//disable most strict options
scalacOptions ~= (_.filterNot(Set(
  "-Ywarn-unused:imports",
  "-Xfatal-warnings"

)))

libraryDependencies ++= {

  val scalazV = "7.2.15"
  val akkaVersion = "2.5.4"
  val akkaHttpV = "10.0.9"
  val scalaTestV = "3.2.0-SNAP9"
  val cassandraV = "3.3.0"
  val nettyV = "4.1.15.Final"

  val gatlingVersion = "3.0.0-SNAPSHOT"

  Seq(

    "com.typesafe.akka" %% "akka-actor" % akkaVersion,

    //"com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    //"com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
    //"com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
    //"com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,

    //"com.typesafe.akka" %% "akka-distributed-data" % akkaVersion,
    //"com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test",

    //"com.typesafe.akka" %% "akka-persistence" % akkaVersion,
    //"com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
    //"com.typesafe.akka" %% "akka-persistence-tck" % akkaVersion,

    //"com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,

    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % "test",
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion  % "test",

    //"com.typesafe.akka" %% "akka-typed" % akkaVersion,
    //"com.typesafe.akka" %% "akka-contrib" % akkaVersion,

    //Akka HTTP

    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV % "test",

    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-jackson" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV,

    //integration alpakka
    "com.lightbend.akka" %% "akka-stream-alpakka-cassandra" % "0.11",

    "io.netty" % "netty-all" % nettyV,
    "io.netty" % "netty-codec" % nettyV,
    "io.netty" % "netty-handler" % nettyV,
    "io.netty" % "netty-transport-native-epoll" % nettyV classifier "linux-x86_64",

    "org.scalaz" %% "scalaz-core" % scalazV,

    "com.datastax.cassandra" % "cassandra-driver-core" % cassandraV,
    "ch.qos.logback" % "logback-classic" % "1.2.3",

    "org.scalactic" %% "scalactic" % scalaTestV % "test",
    "org.scalatest" %% "scalatest" % scalaTestV % "test",

    //integration load tests with gatling
    "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % "it",
    "io.gatling" % "gatling-test-framework" % gatlingVersion % "it"
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
import de.heikoseeberger.sbtheader.license.Apache2_0

headers := Map(
  "scala" -> Apache2_0("2017", "Dmytro Rashko"),
  "conf" -> Apache2_0("2017", "Dmytro Rashko", "#")
)

// enable scala code formatting //
import com.typesafe.sbt.SbtScalariform

import scalariform.formatter.preferences._

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
//val gatlingRepository = "http://repository.gatling.io/ce2dec95-b42a-4518-9371-ad5672b320ad/content/repositories/releases"
//resolvers += "Gatling Corp's Repository" at gatlingRepository

val gatlingRepository = "http://artifactory.autocrm.net/artifactory/oss-sonatype-snapshots"
resolvers += "Gatling Corp's Repository" at gatlingRepository



enablePlugins(GatlingPlugin)
javaOptions in Gatling := overrideDefaultJavaOptions("-Xms64m", "-Xmx256m")
logLevel in Gatling := Level.Error

// enable plugins //
enablePlugins(AutomateHeaderPlugin)

// docker plugin
enablePlugins(JavaServerAppPackaging)

dockerBaseImage := "openjdk:8u141-jre-slim"
dockerRepository := Some("dimetron")
dockerExposedPorts := Vector(8080, 8888)

version.in(Docker) := "latest"
packageName in Docker := "netserver"
daemonUser.in(Docker) := "root"
maintainer.in(Docker) := "Dmytro Rashko"

mappings in Universal += {
  val conf = (resourceDirectory in Compile).value / "application.conf"
  conf -> "application.conf"
}

javaOptions in Universal ++= Seq(
  // -J params will be added as jvm parameters
  "-J-Xms128m",
  "-J-Xmx512m",
  "-J-server",
  "-J-XX:+UseNUMA",
  "-J-XX:+UseCondCardMark",
  "-J-XX:-UseBiasedLocking",
  "-J-Xss1M",
  "-J-XX:+UseParallelGC",

  "-J-XX:+PrintCommandLineFlags",
  "-J-XX:+AggressiveOpts",
  "-J-XX:+UseStringDeduplication",

  "-Dsun.net.inetaddr.ttl=60",
  "-Djava.net.preferIPv4Stack=true"

  // you can access any build setting/task here
  //s"-version=${version.value}"
)

//exclude main method from test coverage report
coverageExcludedPackages := "<empty>;com\\.carmonit\\.server\\.ServerMain;"