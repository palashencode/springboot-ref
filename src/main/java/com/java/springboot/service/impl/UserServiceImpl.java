package com.java.springboot.service.impl;

import com.java.springboot.entities.User;
import com.java.springboot.repository.UserRepository;
import com.java.springboot.service.UserService;
import com.java.springboot.util.MockUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersSortedByDOB() {
        return userRepository.listByDOB();
    }

    @Override
    public String getDBTime() {
        return userRepository.getDBTime();
    }

    @Override
    public List<User> getUsersSortedByName(String name) {
        return userRepository.listUserByName(name);
    }

    @PostConstruct
    public void init(){
        userRepository.saveAll(MockUtil.getUsers());
    }

}
