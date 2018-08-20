package com.example.demo.redis.operations;

import com.example.demo.redis.base.BaseRedisTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.redis.core.ScanOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author nanco
 * -------------
 * hash api
 * -------------
 * @create 2018/8/20
 **/
public class HashOperationTest extends BaseRedisTest {


    @Override
    public void before() {
        operationKey = "coder";
    }

    @Test
    @Override
    public void testSet() {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "jingtj");
        userMap.put("age", "18");
        userMap.put("email", "questionsky1211@gmail.com");
        userMap.put("job", "programmer");
        userMap.put("coder_language", "java");

        stringRedisTemplate.opsForHash().putAll(operationKey, userMap);
        stringRedisTemplate.expire(operationKey, 5, TimeUnit.MINUTES);
    }

    @Test
    @Override
    public void testGet() {
        // key-value
        stringRedisTemplate.opsForHash().entries(operationKey).forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });

        // values
        stringRedisTemplate.opsForHash().values(operationKey).forEach(obj -> {
            System.out.println(obj);
        });

        // scan api
        ScanOptions.ScanOptionsBuilder scanOptionsBuilder = new ScanOptions.ScanOptionsBuilder();
        scanOptionsBuilder.count(2);
        scanOptionsBuilder.match("*");

        stringRedisTemplate.opsForHash().scan(operationKey, scanOptionsBuilder.build()).forEachRemaining((entry) -> {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        });
    }

    @Test
    @Override
    public void testUpdate() {
        stringRedisTemplate.opsForHash().putIfAbsent(operationKey, "school", "hdu");
    }

    @Test
    @Override
    public void testDel() {
        stringRedisTemplate.opsForHash().delete(operationKey, "school");

        Assert.assertEquals(Boolean.FALSE, stringRedisTemplate.opsForHash().hasKey(operationKey, "school"));
    }
}
