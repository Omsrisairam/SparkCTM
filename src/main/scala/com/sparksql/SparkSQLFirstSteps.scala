  package com.sparksql

  import org.apache.spark.{SparkConf, SparkContext}

  object SparkSQLFirstSteps {

    case class Babies(year: String, first_name: String, county: String, sex: String, count: Double)

    def main(args: Array[String]) {
      val conf = new SparkConf()
      conf.set("spark.app.name", "SparkSQLFirstSteps")
      conf.set("spark.master", "local[4]")
      conf.set("spark.ui.port", "36000") // Override the default port
      // Create a SparkContext with this configuration
      val sc = new SparkContext(conf.setAppName("SparkSQlFirstSteps"))

      // Create the SQLContext first from the existing Spark Context
      val sqlContext = new org.apache.spark.sql.SQLContext(sc)
      // Import statement to implicitly convert an RDD to a DataFrame
      import sqlContext.implicits._

      var file = sc.textFile("resources/babynames.csv")

      val splitRDD = file.map(line => line.split(","))


      //skipping header row which has column names
      val header = file.first()
      val fewColumns = file.filter(x => x != header)
      val splitDF = fewColumns.map(line => line.split(",")).map(p => Babies(p(0), p(1), p(2), p(3), p(4).toDouble)).toDF()

      splitDF.registerTempTable("babies")

      val namesCount = sqlContext.sql("select first_name, sum(count) from babies group By first_name order By first_name")
      namesCount.foreach(println)
    }
  }