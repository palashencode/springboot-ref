package com.java.springboot.controllers;

import com.java.springboot.entities.Country;
import com.java.springboot.entities.User;
import com.java.springboot.service.CountryService;
import com.java.springboot.service.LabelService;
import com.java.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crud/")
@Slf4j
public class CrudController {

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @Autowired
    LabelService labelService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/users/sortbydob")
    public List<User> getAllUsersSortedByDob(){
        return userService.getUsersSortedByDOB();
    }

    @GetMapping("/dbtime")
    public String getDBTime(){
        return userService.getDBTime();
    }

}
