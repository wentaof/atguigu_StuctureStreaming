package com.aden.project1;


import java.io.Serializable;

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/3 10:16
 * @Version 1.0.0
 * @Description TODO
 */

public class Ads implements Serializable {
    private long ts ;
    private String province ;
    private String city;
    private int userid;
    private int adsid;

    public Ads(long ts, String province, String city, int userid, int adsid) {
        this.ts = ts;
        this.province = province;
        this.city = city;
        this.userid = userid;
        this.adsid = adsid;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAdsid() {
        return adsid;
    }

    public void setAdsid(int adsid) {
        this.adsid = adsid;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "ts=" + ts +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", userid=" + userid +
                ", adsid=" + adsid +
                '}';
    }
}
