package com.pgz.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.util.List;

/**
 * redis测试
 *
 * @author liquan_pgz@qq.com
 * @date 2021-02-22
 */
public class TestRedis {

    @Test
    public void testPipeline() throws IOException {
        Jedis jedis = new Jedis("192.168.1.165");
        Pipeline pipe = jedis.pipelined();
        pipe.multi();//设置事务
        pipe.set("lin2", "for");//设置string
        //设置hash
        pipe.hset("linhash1", "name", "fff");
        pipe.hset("linhash1", "age", "31");
        pipe.hset("linhash1", "gender", "male");
        Response<List<Object>> response = pipe.exec();//事务获取结果
        pipe.sync();//一定要调用这个才能获取结果
        List<Object> list = response.get();
        for(Object o:list){ System.out.println(o); }
        pipe.close();
        jedis.close();


    }

}
