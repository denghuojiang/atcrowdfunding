package com.hsnay.crowd.test;

import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    final private Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testSet() {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        String key = "good";
        String value = "morning";
        stringStringValueOperations.set(key, value);
    }

    @Test
    public void testExSet() {
        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set("banana", "monkey", 5000, TimeUnit.SECONDS);
    }
}
