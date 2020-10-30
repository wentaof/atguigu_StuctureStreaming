package com.aden.structure_streaming

import org.apache.spark.sql.SparkSession

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/10/29 14:30
 * @Version 1.0.0
 * @Description TODO
 */
object WordCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("structure").getOrCreate()
//    原始表
    val source_df = spark.readStream.format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()




//    结果表
    val result = source_df.writeStream
      .format("console")
      .outputMode("update")
      .start()

    result.awaitTermination()
    spark.stop()
  }
}
