package com.hsnay.spring.boot.springbootautoproject;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


@SpringBootTest
class SpringBootAutoProjectApplicationTests {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    final private Logger logger = LoggerFactory.getLogger(SpringBootAutoProjectApplicationTests.class);

    @Test
    void contextLoads() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "good";
        String value = "morning";
        valueOperations.set(key,value);
        Object o = valueOperations.get(key);
        logger.info(o.toString());

    }
    @Test
    public void test(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set("happy","day");
    }

}
