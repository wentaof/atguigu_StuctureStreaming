package com.aden.project1;


import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.security.Principal;
import java.util.Properties;

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/3 11:06
 * @Version 1.0.0
 * @Description TODO
 */
public class producer {
    public static void main(String[] args) throws InterruptedException {
        utils utils = new utils();

        int freq = 1;
        long cnt = 10L;
        boolean iscontinu = true;

        //创建生产者
        Properties prop = new Properties();
        prop.put("bootstrap.servers","192.168.2.115:9092");
        prop.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer(prop);
        //gson
        Gson gson = new Gson();
        while (cnt > 0 || iscontinu){
            long ts = utils.getTimestamp();
            String provincecity = utils.getProvince();
            String province = provincecity.split("_")[0];
            String city = provincecity.split("_")[1];
            int adsid = utils.getRandomInt(100);
            int userid = utils.getRandomInt(10);
            Ads ads = new Ads(ts, province, city, userid, adsid);
            try {
                ProducerRecord<String, String> record = new ProducerRecord<String, String>("structure_streaming",gson.toJson(ads));
                producer.send(record).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(1000/freq);
            cnt--;
        }

    }




}

