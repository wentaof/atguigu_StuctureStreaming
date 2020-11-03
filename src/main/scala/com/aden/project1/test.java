package com.aden.project1;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author fengwentao@yadingdata.com
 * @date 2020/11/3 17:22
 * @Version 1.0.0
 * @Description TODO
 */
public class test {
    public static void main(String[] args) {
        Jedis jedis1 = new Jedis("127.0.0.1",6379);
        Set<String> set = jedis1.smembers("BlackList-2020-11-03");
        System.out.println(set);

    }
}
