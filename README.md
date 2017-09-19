# Scala World Scala.js workshop
Scala.js project base for Scala World 2017 workshop

# Getting started
First of all make sure you have the necessary tools installed. You'll need the following:

-   Git ([downloads](https://git-scm.com/downloads) for all platforms)
-   Java 8 JDK ([downloads](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) for all
    platforms)
-   SBT (setup for [Linux](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Linux.html) \|
    [Windows](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Windows.html) \|
    [Mac](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Mac.html))

Now you're ready to download and start using the starter project:

1. `git clone https://github.com/ochrons/scalaworld-scalajs.git`
1. `cd scalaworld-scalajs`
1. `sbt updateClassifiers` to download dependencies
1. `git checkout clientserver`
1. `sbt updateClassifiers` to download dependencies for the client-server version
1. `git checkout master` to go back to the client-only version
1. `sbt`
1. \> `~fastOptJS` automatically compiles your code to JS when you make changes
1. In your browser navigate to [http://localhost:12345/target/scala-2.12/classes/index.html](http://localhost:12345/target/scala-2.12/classes/index.html)

There is a more advanced version of the project under the `clientserver` branch which includes, to no one's surprise, a 
web client and a server :)
