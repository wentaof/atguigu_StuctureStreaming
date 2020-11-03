package com.aden.project1

import java.sql.Timestamp

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/3 18:16
 * @Version 1.0.0
 * @Description TODO
 */
object CaseClass {
  case class ads_more(ts:Timestamp, province:String, city:String, userid:Int, adsid:Int, day:String, hour:String)
}
