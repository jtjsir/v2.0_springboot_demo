package com.example.demo.web.validation;

import javax.validation.constraints.NotNull;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 18/9/9
 */
public class SimpleValidation {

    @NotNull(message = "name is allowed to nullable")
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SimpleValidation{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
