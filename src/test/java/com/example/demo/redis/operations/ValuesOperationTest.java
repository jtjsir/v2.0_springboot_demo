package com.example.demo.redis.operations;

import com.example.demo.redis.base.BaseRedisTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author nanco
 * -------------
 * values operation for redis
 * -------------
 * @create 2018/8/20
 **/

public class ValuesOperationTest extends BaseRedisTest {

    @Before
    public void before() {
        operationKey = "coder_name";
    }

    @Test
    public void testSet() {
        // exist 5 minutes
        stringRedisTemplate.opsForValue().set(operationKey, "jingtj", 5, TimeUnit.MINUTES);
    }

    @Test
    public void testGet() {
        String name = stringRedisTemplate.opsForValue().get(operationKey);
        Assert.assertEquals(name, "jingtj");
    }

    @Test
    public void testMultiGet() {
        // use keys command
        Set<String> keysSet = stringRedisTemplate.keys("*");
        keysSet.stream().forEach(key -> {
            System.out.println(key);
        });

        stringRedisTemplate.opsForValue().multiGet(keysSet).forEach(value -> {
            System.out.println(value);
        });

    }

    @Test
    public void testUpdate() {
        // if target key is not exist,it will be created. expect result is jingtj_bob
        stringRedisTemplate.opsForValue().append(operationKey, "_bob");

        Assert.assertEquals("jingtj_bob", stringRedisTemplate.opsForValue().get(operationKey));
    }

    @Test
    public void testDel() {
        stringRedisTemplate.delete(operationKey);

        Assert.assertEquals(Boolean.FALSE, stringRedisTemplate.hasKey(operationKey));
    }
}
