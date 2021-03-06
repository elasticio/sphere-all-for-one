// Comment this to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Play framework integration
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.2")

// IDEA integration
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")
