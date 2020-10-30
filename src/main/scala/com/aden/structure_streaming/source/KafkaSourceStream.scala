package com.aden.structure_streaming.source

import java.util.concurrent.TimeUnit

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

import scala.concurrent.duration.Duration

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/10/30 14:57
 * @Version 1.0.0
 * @Description TODO
 */
object KafkaSourceStream {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("filesource").getOrCreate()
    import spark.implicits._
    val student_schema = StructType(List(StructField("name",StringType), StructField("age",LongType)))
    //    原始表
    val sourceDF = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers","192.168.2.115:9092")
      .option("subscribe","structure_streaming")
      .load()

    val middleDF = sourceDF.map( x=>{
      val value = new String( x.getAs("value"),"utf-8")
      value
    })
      .map(x=>{
        val arr = x.split(" ")
        (arr(0),arr(1))
      }).toDF("name","age")

    val resultdf= middleDF
      .writeStream
      .trigger(Trigger.ProcessingTime(10, TimeUnit.SECONDS))
      .format("console")
      .option("truncate","false") //输出不隐藏
      .start()
    resultdf.awaitTermination()

  }
}
case class student(name:String,age:Int)
