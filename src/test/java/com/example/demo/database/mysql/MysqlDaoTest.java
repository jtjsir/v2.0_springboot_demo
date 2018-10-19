package com.example.demo.database.mysql;

import com.example.demo.database.DatabaseApplication;
import com.example.demo.database.entity.User;
import com.example.demo.database.mysql.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author nanco
 * -------------
 * demo-springboot
 * -------------
 * @create 2018/10/17 17:13
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DatabaseApplication.class})
public class MysqlDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    public void testConnect() {
        Assert.notNull(userDao, "mybatis load fail.");
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setName("jingtj");
        user.setAge(18);
        user.setEmail("nancoasky@gmail.com");
        System.out.println(userDao.saveUser(user));
    }
}
