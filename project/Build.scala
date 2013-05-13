import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "testApp"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "org.twitter4j" % "twitter4j-core" % "3.0.3",
    "org.twitter4j" % "twitter4j-stream" % "3.0.3",
    "org.scalanlp" % "nak" % "1.1.1",
    "com.typesafe.akka" %% "akka-actor" % "2.1.2",
    "commons-codec" % "commons-codec" % "1.7",
    "org.apache.lucene" % "lucene-core" % "4.2.0",
    "org.apache.lucene" % "lucene-analyzers-common" % "4.2.0",
    "org.apache.lucene" % "lucene-queryparser" % "4.2.0",
    "de.bwaldvogel" % "liblinear" % "1.92",
    "com.cybozu.labs" % "langdetect" % "1.1-20120112" 
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
