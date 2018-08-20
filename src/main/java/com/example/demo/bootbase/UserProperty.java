package com.example.demo.bootbase;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author nanco
 * @create 2018/8/13
 **/
@ConfigurationProperties(prefix = "user.custom")
public class UserProperty {

    private String username;

    private String nickname;

    private String email;

    private String password;

    private String job;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "UserProperty{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
