package com.itheima.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-redis.xml")
public class redisTest {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Test
    public void redisTest(){
        redisTemplate.opsForValue().set("username","oliverHoo",5, TimeUnit.MINUTES);
        String username = redisTemplate.opsForValue().get("username");
        System.out.println(username);

    }}
