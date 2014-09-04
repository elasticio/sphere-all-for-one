import play.Project._

organization := "io.sphere.shop"

name := "sphere-fedora"

version := "1.0-SNAPSHOT"

playJavaSettings

libraryDependencies ++= Seq(
  javaCore,
  javaJdbc,
  "io.sphere" %% "sphere-play-sdk" % "0.65.0" withSources(),
  "javax.mail" % "mail" % "1.4.7",
  "org.jsoup" % "jsoup" % "1.7.1",
  "org.mockito" % "mockito-all" % "1.9.5" % "test"
)

lessEntryPoints := baseDirectory.value / "app" / "assets" / "stylesheets" * "*.less"

templatesImport ++= Seq(
  "utils.ViewHelper._",
  "utils.PrintUtils._",
  "forms._",
  "io.sphere.client.model._",
  "io.sphere.client.filters._",
  "io.sphere.client.shop.model._"
)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")