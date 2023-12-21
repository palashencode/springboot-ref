package com.java.springboot.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.springboot.entities.User;
import com.java.springboot.service.CountryService;
import com.java.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/json/")
@Slf4j
public class JSONController {

    private final static String jsonPath = "src/main/resources/json/";

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @GetMapping("file/users")
    public void  saveUsersToCSVFolder() throws IOException {
        List<User> users = userService.getUsers();

        ObjectMapper mapper = new ObjectMapper();
//        mapper.getFactory().configure()
        mapper.registerModule(new JavaTimeModule());

//        Object to JSON in file
        mapper.writeValue(new File(jsonPath+"Users.json"), users);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        log.info(json);
    }

    @GetMapping("read/users")
    public List<User>  readUsersToCSVFolder() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<User> users = mapper.readValue(new File(jsonPath+"Users.json"), List.class);
        return users;
    }

}
