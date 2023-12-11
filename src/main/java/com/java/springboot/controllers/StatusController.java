package com.java.springboot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class StatusController {
    @Operation(description = "description, this is a status endpoint", summary = "summary, this is the status endpoint")
    @GetMapping("status")
    public String getStatus(){
        return "Status, OK - "+ Instant.now();
    }
}
