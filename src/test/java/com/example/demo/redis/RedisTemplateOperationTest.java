package com.example.demo.redis;

import com.example.demo.redis.operations.RedisApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/8/17
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApplication.class})
public class RedisTemplateOperationTest {

    private TimeUnit unit_minute = null;

    private int key_timeout = 0;


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Before
    public void before() {
//        Set<String> keys = stringRedisTemplate.keys("*");
//        keys.stream().forEach((key) -> {
//            stringRedisTemplate.delete(key);
//        });

        unit_minute = TimeUnit.MINUTES;

        key_timeout = 5;
    }

    @Test
    public void testHSet() {
        Map<String, String> accountMap = new HashMap<>(8);
        accountMap.put("name", "jingtj");
        accountMap.put("age", "24");
        accountMap.put("email", "questionsky1211@gmail.com");
        accountMap.put("sex", "male");
        accountMap.put("job", "programmer");
        accountMap.put("job_language", "java");


        stringRedisTemplate.opsForHash().putAll("userInfo", accountMap);
    }

    @Test
    public void testHGet() {
        stringRedisTemplate.opsForHash().entries("userInfo").forEach((key, value) -> {
            System.out.println(key + "=" + value);
        });
    }

    @Test
    public void testGet() {
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }

    @Test
    public void testSet() {
        stringRedisTemplate.opsForValue().set("name", "jingtj", key_timeout, unit_minute);
        stringRedisTemplate.boundValueOps("name").expire(key_timeout, unit_minute);
    }

    @Test
    public void testLpush() {
        stringRedisTemplate.opsForList().leftPushAll("program_language", new String[]{"java", "php", "object-c", "nodejs", "c++", "c", "python"});
        stringRedisTemplate.opsForList().getOperations().boundListOps("program_language").expire(key_timeout, unit_minute);
    }

    @Test
    public void testLrange() {
        stringRedisTemplate.opsForList().range("program_language", 0, 10)
                .forEach((value) -> {
                    System.out.println(value);
                });
    }

    @Test
    public void testSadd() {
        stringRedisTemplate.opsForSet().add("sex_type", new String[]{"male", "female", "male", "female"});
        stringRedisTemplate.boundSetOps("sex_type").expire(key_timeout, unit_minute);
    }

    @Test
    public void testSmemebers() {
        stringRedisTemplate.opsForSet().members("sex_type").forEach((type) -> {
            System.out.println("sex: " + type);
        });
    }

    @Test
    public void testZadd() {
        stringRedisTemplate.opsForZSet().add("score", "math", 90);
        stringRedisTemplate.opsForZSet().add("score", "chinese", 85);
        stringRedisTemplate.opsForZSet().add("score", "science", 87);
        stringRedisTemplate.opsForZSet().add("score", "english", 95);

        stringRedisTemplate.boundZSetOps("score").expire(key_timeout, unit_minute);
    }

    @Test
    public void testZrange() {
        // default sort by score asc
        stringRedisTemplate.opsForZSet().rangeByScore("score", 0, 100).forEach((course) -> {
            System.out.println(course);
        });

        // get sort object desc
        stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores("score", 0, 100).forEach(action -> {
            System.out.println(action.getValue() + ":" + action.getScore());
        });
    }
}
