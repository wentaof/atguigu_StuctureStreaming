package com.aden.structure_streaming.source

import java.util.concurrent
import java.util.concurrent.TimeUnit

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.ProcessingTime
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/10/29 17:19
 * @Version 1.0.0
 * @Description TODO
 */
object FileSource {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("filesource").getOrCreate()

    val student_schema = StructType(List(StructField("name",StringType), StructField("age",LongType)))
//    原始表
    val sourceDF = spark
      .readStream
      .format("csv")
      .schema(student_schema)
      .load("C:\\Users\\fengwentao\\Desktop\\testData\\structureStreaming")
    case class student(name:String,age:Int)
    import spark.implicits._
//    DF的写法
//    val middle_result = sourceDF.groupBy("name").count()



//  结果表
    import scala.concurrent.duration._
    val result = sourceDF
      .writeStream
      .outputMode("update")
      .format("console")
//      .trigger(ProcessingTime.create(10,TimeUnit.SECONDS))
      .trigger(ProcessingTime.create(10 , TimeUnit.SECONDS))
      .start()

    result.awaitTermination()
    spark.stop()

  }
}
