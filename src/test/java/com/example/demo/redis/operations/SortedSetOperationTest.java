package com.example.demo.redis.operations;

import com.example.demo.redis.base.BaseRedisTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisZSetCommands;

import java.util.concurrent.TimeUnit;

/**
 * @author nanco
 * -------------
 * sorted set api
 * -------------
 * @create 2018/8/20
 **/
public class SortedSetOperationTest extends BaseRedisTest {

    public static void main(String[] args) {
        System.out.println(Integer.valueOf(100).byteValue());
    }

    @Before
    @Override
    public void before() {
        operationKey = "course";
    }

    @Test
    @Override
    public void testSet() {
        // default sorted by score asc,sorted by ascii lex when the score same
        stringRedisTemplate.opsForZSet().add(operationKey, "Math", 100);
        stringRedisTemplate.opsForZSet().add(operationKey, "Chinese", 89);
        stringRedisTemplate.opsForZSet().add(operationKey, "English", 86);
        stringRedisTemplate.opsForZSet().add(operationKey, "Science", 95);
        stringRedisTemplate.opsForZSet().add(operationKey, "Music", 80);

        stringRedisTemplate.opsForZSet().add("city", "London", 1);
        stringRedisTemplate.opsForZSet().add("city", "Paris", 1);
        stringRedisTemplate.opsForZSet().add("city", "Tokyo", 1);
        stringRedisTemplate.opsForZSet().add("city", "Beijing", 1);
        stringRedisTemplate.opsForZSet().add("city", "NewYork", 1);

        stringRedisTemplate.expire(operationKey, 5, TimeUnit.MINUTES);
        stringRedisTemplate.expire("city", 5, TimeUnit.MINUTES);
    }

    @Test
    @Override
    public void testGet() {
        // top 3
        stringRedisTemplate.opsForZSet().reverseRangeByScore(operationKey, 0, 100, 0, 3).forEach(course -> {
            System.out.println(course);
        });


        // course+score
        stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(operationKey, 0, 100).forEach(tuple -> {
            System.out.println(tuple.getValue() + "-->" + tuple.getScore());
        });
    }

    @Test
    public void testZrangebyLex() {
        // rangebylex, it aim to <b>same score with several values</b>
        RedisZSetCommands.Range range = RedisZSetCommands.Range.range().gte("Beijing").lte("Paris");
        stringRedisTemplate.opsForZSet().rangeByLex("city", range).forEach(city -> {
            System.out.println(city);
        });

        /**
         * echo result is Beijing,London,NewYork,Paris
         */
    }

    @Override
    public void testUpdate() {

    }

    @Test
    @Override
    public void testDel() {
        long courseLen = stringRedisTemplate.opsForZSet().size(operationKey);
        long removeLen = stringRedisTemplate.opsForZSet().removeRange(operationKey, 0, courseLen);

        Assert.assertEquals(courseLen, removeLen);
    }
}
