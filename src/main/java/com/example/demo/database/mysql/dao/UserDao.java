package com.example.demo.database.mysql.dao;

import com.example.demo.database.entity.User;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/10/17
 **/
public interface UserDao {

    int saveUser(User user);
}
