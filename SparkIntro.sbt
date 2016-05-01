name := "Log Analyzer"

version := "1.0"

scalaVersion := "2.10.5"

mainClass in (Compile, run) := Some("com.sparkrddexample.SingleBabyName")

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.1"