name := "challenge2"

version := "1.0"

scalaVersion := "2.11.0"


libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.5" % "test",
  "com.github.tototoshi" %% "scala-csv" % "1.3.3",
  "org.scala-lang" % "scala-reflect" % "2.11.0",
  "org.typelevel" %% "cats" % "0.8.0"
)

scalacOptions in Test ++= Seq("-Yrangepos")