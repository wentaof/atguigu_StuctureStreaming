package com.aden.project1

import java.sql.Timestamp
import com.aden.project1.CaseClass.ads_more
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{Dataset, ForeachWriter, Row, SparkSession}
import redis.clients.jedis.Jedis

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/3 16:23
 * @Version 1.0.0
 * @Description TODO
 */
object BlackListApp {
  def BlackList2Redis(spark: SparkSession, ds: Dataset[ads_more]) = {

    //过滤之前 应该先把黑名单里面的数据删除掉，减少计算量
    val jedis1 = new Jedis("127.0.0.1",6379)
    val bl_userid = jedis1.smembers("BlackList-2020-11-03")

    val filterds = ds.filter(line=>{
      !bl_userid.contains(line.adsid)
    })


    filterds.createOrReplaceTempView("ds")
    spark.sql(
      """
        |select
        |day,userid,count(1) as freq
        |from ds
        |group by day,userid
        |having freq > 10
        |""".stripMargin)
      .writeStream
      .outputMode("complete")
      //      .format("console")
      .foreach(new ForeachWriter[Row] {
        var jedis: Jedis = _

        override def open(partitionId: Long, epochId: Long): Boolean = {
          // 初始化连接redis
          jedis = new Jedis("localhost", 6379)
          true
        }

        override def process(row: Row): Unit = {
          val day = row.getAs[String]("day")
          val userid = row.getAs[Int]("userid").toString

          val setName = s"BlackList-$day"
          jedis.sadd(setName, userid)
          // 处理每条数据
        }

        override def close(errorOrNull: Throwable): Unit = {
          // 关闭redis连接
          if (jedis != null && jedis.isConnected) {
            jedis.close()
          }

        }
      })
      .trigger(Trigger.ProcessingTime(1000))
      .start()
      .awaitTermination()

    filterds.show(false)
  }
}

