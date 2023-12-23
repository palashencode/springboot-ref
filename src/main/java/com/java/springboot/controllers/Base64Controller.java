package com.java.springboot.controllers;


import com.java.springboot.service.CountryService;
import com.java.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/base64/")
@Slf4j
public class Base64Controller {

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @Operation(summary = "uses - java.util.Base64, which was added in java 8")
    @GetMapping("encode/text")
    public String  encodeBase64(@RequestParam(name = "value") String value) throws IOException {
        return  Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("decode/text")
    public String decodeBase64(@RequestParam(name = "value") String value) throws IOException {
        return new String(Base64.getDecoder().decode(value));
    }

    @Operation(summary = "uses - java.util.Base64, which was added in java 8")
    @GetMapping("encode/url")
    public String  encodeURLBase64(@RequestParam(name = "value") String value) throws IOException {
        return  Base64.getUrlEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("decode/url")
    public String decodeURLBase64(@RequestParam(name = "value") String value) throws IOException {
        return new String(Base64.getUrlDecoder().decode(value));
    }

}
