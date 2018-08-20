package com.example.demo.redis.operations;

import com.example.demo.redis.base.BaseRedisTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author nanco
 * -------------
 * set operation api
 * -------------
 * @create 2018/8/20
 **/
public class SetOperationTest extends BaseRedisTest {

    @Before
    public void before() {
        operationKey = "code_language";
    }

    @Test
    @Override
    public void testSet() {
        String[] language = new String[]{"java", "php", "javascript", "ruby", "go", "scala", "python"};
        stringRedisTemplate.opsForSet().add(operationKey, language);

        String[] usedLanguage = new String[]{"java", "javascript", "html5"};
        stringRedisTemplate.opsForSet().add("used_language", usedLanguage);

        stringRedisTemplate.expire(operationKey, 5, TimeUnit.MINUTES);
        stringRedisTemplate.expire("used_language", 5, TimeUnit.MINUTES);
    }

    @Test
    @Override
    public void testGet() {
        stringRedisTemplate.opsForSet().members(operationKey).forEach(language -> {
            System.out.println(language);
        });
    }

    @Test
    @Override
    public void testUpdate() {
        // find difference values in two set
        stringRedisTemplate.opsForSet().difference(operationKey, "used_language").forEach(diffValue -> {
            System.out.println(diffValue);
        });

        // find all values in two set
        stringRedisTemplate.opsForSet().union(operationKey, "used_language").forEach(unionValue -> {
            System.out.println(unionValue);
        });
    }

    @Test
    @Override
    public void testDel() {
        Assert.assertEquals(Boolean.TRUE , stringRedisTemplate.opsForSet().isMember(operationKey , "java"));

        stringRedisTemplate.opsForSet().remove(operationKey , "java") ;
    }
}
