resolvers += "Artima plugins repository" at "http://repo.artima.com/releases"

resolvers += "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "bintray-sbt-plugin-releases" at "http://dl.bintray.com/content/sbt/sbt-plugin-releases"

resolvers += "artima plugins repository" at "http://repo.artima.com/releases"

resolvers += Resolver.url("rtimush/sbt-plugin-snapshots", new URL("https://dl.bintray.com/rtimush/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)

resolvers += "Typesafe" at "http://repo.typesafe.com/typesafe/releases/"


addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.0-M8")

// to kill and reload the spawned JVM
// see: https://github.com/spray/sbt-revolver
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")

// to format scala source code
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

// enable updating file headers eg. for copyright
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.5.1")

//check latest dependencies
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.0")

//docker packaging
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.0-M8")
