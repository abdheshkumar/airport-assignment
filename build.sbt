name := "airport_problem"

version := "1.0"

lazy val `airport_problem` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc, cache, ws,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  specs2 % Test)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"