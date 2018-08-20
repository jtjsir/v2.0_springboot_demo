package com.example.demo.redis.operations;

import com.example.demo.redis.base.BaseRedisTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author nanco
 * -------------
 * list api
 * -------------
 * @create 2018/8/20
 **/
public class ListOperationTest extends BaseRedisTest {
    @Before
    @Override
    public void before() {
        operationKey = "student";
    }

    @Test
    @Override
    public void testSet() {
        String[] studio103 = new String[]{"liuming", "lvzq", "lixw", "jingtj"};
        // recurse the element to push from left orientation
        stringRedisTemplate.opsForList().leftPushAll(operationKey, studio103);

        // recurse the element to push from right orientation
        String[] studio105 = new String[]{"wanghk", "wangms", "yangkf", "wuwei"};
        stringRedisTemplate.opsForList().rightPushAll(operationKey, studio105);

        stringRedisTemplate.expire(operationKey, 5, TimeUnit.MINUTES);
    }

    @Test
    @Override
    public void testGet() {
        long size = stringRedisTemplate.opsForList().size(operationKey);
        stringRedisTemplate.opsForList().range(operationKey, 0, size).forEach(student -> {
            System.out.println(student);
        });
    }

    @Test
    @Override
    public void testUpdate() {
        stringRedisTemplate.opsForList().set(operationKey, 0, "jingtj_bob");
        Assert.assertEquals("jingtj_bob", stringRedisTemplate.opsForList().index(operationKey, 0));
    }

    @Test
    @Override
    public void testDel() {
        Assert.assertEquals("jingtj_bob", stringRedisTemplate.opsForList().leftPop(operationKey));
        Assert.assertEquals("wuwei", stringRedisTemplate.opsForList().rightPop(operationKey));
    }
}
