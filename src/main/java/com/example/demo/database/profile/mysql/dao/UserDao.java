package com.example.demo.database.profile.mysql.dao;

import com.example.demo.database.profile.entity.User;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/10/17
 **/
public interface UserDao {

    int saveUser(User user);
}
