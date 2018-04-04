package com.model;

import com.util.Data;

import java.lang.reflect.Field;
import java.util.List;

public class test {

    public static void main(String[] args) {
        List<User> userList = Data.QueryT("select * from user", null, User.class);
        for (User user : userList) {
            System.out.println(user.getName());
        }
    }
}
