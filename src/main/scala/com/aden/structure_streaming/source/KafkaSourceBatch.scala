package com.aden.structure_streaming.source

import org.apache.spark.sql.SparkSession

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/10/30 17:43
 * @Version 1.0.0
 * @Description TODO
 */
object KafkaSourceBatch {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("KafkaSourceBatch").master("local[*]").getOrCreate()

    val src_df = spark.read
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "structure_streaming")
      .load()

    src_df
        .selectExpr("cast(value as string) as v")
      .show()

  }
}
