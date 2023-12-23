package com.java.springboot.controllers;


import com.java.springboot.entities.User;
import com.java.springboot.entities.xmllist.Users;
import com.java.springboot.service.CountryService;
import com.java.springboot.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/xml/")
@Slf4j
public class XMLController {

    private final static String xmlPath = "src/main/resources/xml/";

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @GetMapping("read/users")
    public Users readUsersToXMLFolder() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Users.class);
        return (Users) context.createUnmarshaller()
                .unmarshal(new FileReader(xmlPath+"Users.xml"));
    }

    @GetMapping("file/users")
    public void  saveUsersToXMLFolder() throws JAXBException {
        List<User> users = userService.getUsers();
        Users lists = new Users();
        lists.setUsers(users);

        JAXBContext context = JAXBContext.newInstance(Users.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(lists, new File(xmlPath+"Users.xml"));
    }

    @GetMapping("download/users")
    public void  downloadUsersToXMLFolder(HttpServletResponse response) throws JAXBException, IOException {

        // set response headers
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "Users.xml" + "\"");
        response.setCharacterEncoding("UTF-8");

        List<User> users = userService.getUsers();
        Users lists = new Users();
        lists.setUsers(users);

        JAXBContext context = JAXBContext.newInstance(Users.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(lists, response.getOutputStream());
    }

}
