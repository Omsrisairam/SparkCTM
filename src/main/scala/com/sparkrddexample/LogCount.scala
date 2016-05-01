package com.sparkrddexample


import org.apache.spark.{SparkConf, SparkContext}

object LogCount{
  def main(args: Array[String]) {
    val logFile = "resources/README.md"
    val conf = new SparkConf().setAppName("Spark Log Analyzer")
    conf.set("spark.master", "local[4]")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
  }
}