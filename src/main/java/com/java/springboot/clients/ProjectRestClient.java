package com.java.springboot.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ProjectRestClient {

    @Autowired
    RestTemplate restTemplate;

    public <T> T quickGet(String path, Class<T> clazz){
        ResponseEntity<T> response = restTemplate.getForEntity(path, clazz);
        return response.getBody();
    }

}
