import Dependencies._

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "in.varadharajan",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "playground",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.github.ichoran" %% "thyme" % "0.1.2-SNAPSHOT"
  )
