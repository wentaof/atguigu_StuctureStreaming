package com.aden.project1

import org.apache.spark.sql.{Dataset, SparkSession}
import com.aden.project1.CaseClass.ads_more
import redis.clients.jedis.Jedis
/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/3 18:09
 * @Version 1.0.0
 * @Description TODO
 */
object AdsClickCountApp {

  def caldayprovicead(spark: SparkSession, toAds_more_df: Dataset[ads_more]) = {
    import spark.implicits._
    toAds_more_df.groupBy("day","province","city","adsid").count()
      .writeStream
//      .format("console")
      .foreachBatch((ds,no) =>{
        ds.persist()
        ds.foreachPartition(p=>{
          val jedis = new Jedis("localhost",6379)
          p.foreach(r=> {
            val day = r.getAs[String]("day")
            val province = r.getAs[String]("province")
            val city = r.getAs[String]("city")
            val adsid = r.getAs[Int]("adsid").toString
            val freq = r.getAs[Long]("count").toString
            val key = "adsclick"
            val field = s"${day}:${province}:${city}:${adsid}"
            val value = freq
            jedis.hset(key, field, value)
            println(s"当前批次：$no")
          })
        })
        ds.unpersist()
      })
      .outputMode("complete")
      .start()
      .awaitTermination()

  }
}
