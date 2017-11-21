import Dependencies._

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "in.varadharajan",
      scalaVersion := "2.10.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "playground",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.eclipse.jetty" % "jetty-client" % "9.3.8.v20160314",
    libraryDependencies += "org.eclipse.jetty.http2" % "http2-http-client-transport" % "9.3.8.v20160314",
    libraryDependencies += "org.eclipse.jetty.http2" % "http2-client" % "9.3.8.v20160314",
    libraryDependencies += "com.storm-enroute" %% "scalameter-core" % "0.8.2"
  )
