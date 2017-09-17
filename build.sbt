enablePlugins(
  ScalaJSPlugin,   // for Scala.js support in this project
  WorkbenchPlugin  // for easy browser deployment
)

name := "Scala World workshop"
scalaVersion := "2.12.3"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1", // Provides a facade for browser DOM functionality
  "com.lihaoyi"  %%% "scalatags"   % "0.6.7"  // Easy-to-use HTML building library
)
