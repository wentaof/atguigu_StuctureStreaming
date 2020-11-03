package com.aden.structure_streaming.join_other



import org.apache.spark.sql.SparkSession

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/2 10:04
 * @Version 1.0.0
 * @Description TODO
 */
object steam_static {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").appName("spark_sc").getOrCreate()
    val sc = spark.sparkContext
    val static_df = sc.parallelize(Seq("fwt,18","lbb,19","fengwentao,20")).map(_.split(","))

  }
}
