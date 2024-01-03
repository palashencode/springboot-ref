package com.java.springboot.service;

import com.java.springboot.entities.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    String getDBTime();
    List<User> getUsersSortedByDOB();
    List<User> getUsersSortedByName(String column);

}
