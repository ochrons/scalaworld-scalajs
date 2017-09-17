import sbt._
import Keys._

val commonSettings = Seq(
  scalaVersion := "2.12.3"
)

lazy val shared = crossProject
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings: _*)
  .settings(
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies ++= Seq(
      "io.circe" %%% "circe-core" % "0.9.0-M1",
      "io.circe" %%% "circe-generic" % "0.9.0-M1",
      "io.circe" %%% "circe-parser" % "0.9.0-M1"
    )
  )

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val client = project
  .in(file("client"))
  .enablePlugins(
    ScalaJSPlugin // for Scala.js support in this project
  )
  .settings(commonSettings: _*)
  .settings(
    name := "Scala World workshop",
    // This is an application with a main method
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.3", // Provides a facade for browser DOM functionality
      "com.lihaoyi" %%% "scalatags" % "0.6.7" // Easy-to-use HTML building library
    )
  )
  .dependsOn(sharedJs)

lazy val server = project
  .in(file("server"))
  .enablePlugins(
    SbtWeb,
    JavaAppPackaging
  )
  .settings(commonSettings: _*)
  .settings(
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    WebKeys.packagePrefix in Assets := "public/",
    managedClasspath in Runtime += (packageBin in Assets).value,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.0.10" // HTTP server by Akka
    )
  )
  .dependsOn(sharedJvm)

// loads the server project at sbt startup
onLoad in Global := (Command
  .process("project server", _: State)) compose (onLoad in Global).value
