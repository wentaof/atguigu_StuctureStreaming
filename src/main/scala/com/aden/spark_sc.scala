package com.aden

import org.apache.spark.sql.SparkSession

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/2 10:01
 * @Version 1.0.0
 * @Description TODO
 */
object spark_sc {
  def getspark = {
    val spark = SparkSession.builder().master("local").appName("spark_sc").getOrCreate()
    spark
  }
}
