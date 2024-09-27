package com.java.springboot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@Slf4j
public class StatusController {

    @Operation(description = "description, this is a status endpoint", summary = "summary, this is the status endpoint")
    @GetMapping("status")
    public String getStatus(){
        log.trace("TRACE Status Call : {}",Instant.now());
        log.debug("DEBUG Status Call : {}",Instant.now());
        log.info("INFO Status Call : {}",Instant.now());
        log.warn("WARN Status Call : {}",Instant.now());
        log.error("ERROR Status Call : {}",Instant.now());
        return "Status, OK - "+ Instant.now();
    }
}
