  package com.sparkrddexample

  //Year,First Name,County,Sex,Count
  import org.apache.spark.{SparkConf, SparkContext}

  object SingleBabyNameAvg {

    def main(args: Array[String]) {
      val conf = new SparkConf()
      conf.set("spark.app.name", "My Spark App")
      conf.set("spark.master", "local[4]")
      conf.set("spark.ui.port", "36000") // Override the default port

      // Create a SparkContext with this configuration
      val sc = new SparkContext(conf.setAppName("SingleBabyNameAvg"))
      //var file = sc.textFile(args(0))
      //if(args.length < 2){
        var file = sc.textFile("resources/babynames.csv")
      //}

      val splitRDD = file.map(line => line.split(","))

      val fewColumns = splitRDD.map(x => (x(1), x(4))).filter(x => (x._1.contains("SOPHIA")))
      //8689 - number of sophia records
      //50796 - total number of records
      val countByName = fewColumns.map { case (name, count) => (name, count.toInt) }.values.sum()
      println("SUM of total number of records whose name is sophia "+countByName)

      //total number of keys
      val noOfKeys = fewColumns.map { case (name, count) => (name, count.toInt) }.countByKey().map { case (name, count) => count }.sum

      println("total numer of keys that have Sophia " + noOfKeys)
      //compute average of single baby name
      val mean = countByName / noOfKeys
      println(mean)
    }
  }
