package com.example.demo.redis.base;

import com.example.demo.redis.operations.RedisApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author nanco
 * -------------
 * base test
 * -------------
 * @create 2018/8/20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisApplication.class})
public abstract class BaseRedisTest {
    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    protected String operationKey;

    /**
     * set operation key
     */
    public abstract void before();

    /**
     * set/add api
     */
    public abstract void testSet();

    /**
     * get api
     */
    public abstract void testGet();

    /**
     * update api
     */
    public abstract void testUpdate();

    /**
     * delete api
     */
    public abstract void testDel();
}
