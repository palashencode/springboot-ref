package com.java.springboot.controllers;

import com.java.springboot.entities.User;
import com.java.springboot.entities.protobuf.ProtocolBufferWrapper;
import com.java.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/protobuf/")
@Slf4j
public class ProtocolBufferController {

    private final static String PROTOCOLBUF_PATH = "src/main/resources/protobuf/binary/";

    @Autowired
    UserService userService;

    @GetMapping("file/users")
    String saveUsersIntoDisk() throws IOException {
        List<User> userList = userService.getUsers();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        List<ProtocolBufferWrapper.User> protocolBufferUsers = userList.stream()
                .map( u -> {
                   return ProtocolBufferWrapper.User.newBuilder()
                            .setId(u.getId())
                            .setName(u.getName())
                            .setUserName(u.getUserName())
                            .setCity(u.getCity())
                            .setDescription(u.getDescription())
                            .setDateOfJoining(u.getDateOfJoining().format(df))
                            .setExactDob(u.getExactDob().format(dtf))
                            .setStatus(u.getStatus())
                            .build();
                            }).collect(Collectors.toList());
        ProtocolBufferWrapper.Users users = ProtocolBufferWrapper.Users.newBuilder().addAllUser(protocolBufferUsers).build();
        try(FileOutputStream out = new FileOutputStream(PROTOCOLBUF_PATH+"Users.protobuf")){
            users.writeTo(out);
        }
        return "user data saved into protocol buffer in disk";
    }

    @GetMapping("/read/users")
    List<User> readUsers() throws IOException {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        ProtocolBufferWrapper.Users users = ProtocolBufferWrapper.Users
                                .newBuilder()
                                .mergeFrom(new FileInputStream(PROTOCOLBUF_PATH+"Users.protobuf"))
                                .build();

        List<ProtocolBufferWrapper.User> protocolBufferUsers = users.getUserList();
        List<User> userList = protocolBufferUsers.stream().map( u -> {
                return User.builder().id(u.getId())
                        .name(u.getName())
                        .userName(u.getUserName())
                        .city(u.getCity())
                        .description(u.getDescription())
                        .dateOfJoining(LocalDate.parse(u.getDateOfJoining(), df))
                        .exactDob(ZonedDateTime.parse(u.getExactDob(), dtf))
                        .status(u.getStatus())
                        .build();
        }).collect(Collectors.toList());
        return userList;
    }
}
