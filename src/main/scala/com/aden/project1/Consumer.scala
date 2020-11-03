package com.aden.project1

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import com.google.gson.Gson
import org.apache.spark.sql.{Dataset, Row, SparkSession}
import org.apache.spark.sql.streaming.Trigger
import com.aden.project1.CaseClass.ads_more
import scala.collection.mutable.ListBuffer
import com.aden.project1.BlackListApp.BlackList2Redis
import com.aden.project1.AdsClickCountApp.caldayprovicead
/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/3 14:24
 * @Version 1.0.0
 * @Description TODO
 */
object Consumer {



  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("xx").master("local[*]").getOrCreate()
    val src_df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.2.115:9092")
      .option("subscribe", "structure_streaming")
      .option("startingOffsets","latest")
      .load()

    val sc = spark.sparkContext

    import spark.implicits._
    val toAds_more_df = src_df
      .selectExpr("cast( value as string) as ads_str")
      .mapPartitions(toAds)
//map的方式 效率低
//        .map(str =>{
//          val gson = new Gson()
//          val ads = gson.fromJson(str.getAs[String]("ads_str"),classOf[Ads])
//          ads.getProvince
//        })
        .withWatermark("ts","2 second")


//    BlackList2Redis(spark,toAds_more_df)
    caldayprovicead(spark,toAds_more_df)
//    toAds_more_df.writeStream
//        .format("console")
//      .option("truncate",false)
//      .outputMode("update")
//      .trigger(Trigger.ProcessingTime(1000 * 1))
//      .start()
//      .awaitTermination()

  }

  def toAds(iter: Iterator[Row])={
    val gson = new Gson()
    val buffer = ListBuffer[ads_more]()
    val fmt_day = new SimpleDateFormat("yyyy-MM-dd")
    val fmt_hour = new SimpleDateFormat("HH:mm")
    val timestamp_fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    iter.foreach(x=>{
      val ads = gson.fromJson(x.getAs[String]("ads_str"),classOf[Ads])
      val ts = ads.getTs
//      val timestamp = timestamp_fmt.format(new Date(ts))
      val timestamp = new Timestamp(ads.getTs)
      val dayString = fmt_day.format(new Date(ts))
      val hmString = fmt_hour.format(new Date(ts))
      val ads_add = ads_more(timestamp,ads.getProvince,ads.getCity,ads.getUserid,ads.getAdsid,dayString,hmString)
      buffer.append(ads_add)
    })
    buffer.toIterator
  }
}


