package com.aden.structure_streaming.source

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/10/30 17:55
 * @Version 1.0.0
 * @Description TODO
 */
object RateSource {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("KafkaSourceBatch").master("local[*]").getOrCreate()

    val srcdf = spark.readStream
      .format("rate")
      .option("rowsPerSecond",100)
      .option("rampUpTime",2)
      .option("numPartitions",2)
        .load()
    srcdf.writeStream
      .format("console")
      .outputMode("update")
      .option("truncate", false)
      .trigger(Trigger.ProcessingTime("10 seconds"))
      .start()
      .awaitTermination()
  }
}
