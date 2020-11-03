package com.aden.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/3 10:24
 * @Version 1.0.0
 * @Description TODO
 */
public class utils {
    static Random random;
    static HashMap<String, ArrayList<String>> proviceCity;
    static ArrayList<String> provinceList;
    //时间戳
    public long getTimestamp(){
        return System.currentTimeMillis();
    }
    //随机数
    public int getRandomInt(int size){
        if(random == null){
            random = new Random();
        }
        return random.nextInt(size);

    }

    public String getProvince(){
        if(proviceCity == null){
            proviceCity = new HashMap<String, ArrayList<String>>();
            ArrayList<String> city = new ArrayList<String>();
            city.add("黑龙江");
            city.add("吉林");
            city.add("辽宁");
            proviceCity.put("东北地区",city);

            ArrayList<String> city1 = new ArrayList<String>();
            city1.add("北京");
            city1.add("天津");
            city1.add("河北");
            city1.add("山西");
            city1.add("内蒙古");
            proviceCity.put("华北地区",city1);

            ArrayList<String> city2 = new ArrayList<String>();
            city2.add("河南");
            city2.add("湖北");
            city2.add("湖南");
            proviceCity.put("华中地区",city2);
            provinceList = new ArrayList<String>();
            provinceList.add("东北地区");
            provinceList.add("华北地区");
            provinceList.add("华中地区");
        }

        String provice = provinceList.get(getRandomInt(3));
        ArrayList<String> citys = proviceCity.get(provice);
        String city = citys.get(getRandomInt(citys.size()));

        return provice+"_"+city;
    }






    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }

}
