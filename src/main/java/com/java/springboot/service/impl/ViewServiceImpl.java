package com.java.springboot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.springboot.model.RowData;
import com.java.springboot.service.ViewService;
import com.java.springboot.service.processor.CatFactProcessor;
import com.java.springboot.service.processor.CoinDeskJsonProcessor;
import com.java.springboot.service.processor.DogImageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ViewServiceImpl implements ViewService {

    @Autowired
    CoinDeskJsonProcessor coinDeskJsonProcessor;

    @Autowired
    DogImageProcessor dogImageProcessor;

    @Autowired
    CatFactProcessor catFactProcessor;

    @Autowired
    RestTemplate restTemplate; // = new RestTemplate();

    @Override
    public long getTime() {
        return Instant.now().toEpochMilli();
    }

    @Override
    public List<RowData> getFeatures() {
        List<RowData> list = new ArrayList<>();
        list.add(new RowData("Microservices","Reference Rest Spring Boot App"));
        list.add(new RowData("Freemarker","Serve Freemarker Templates"));
        list.add(new RowData("Links",
                "<a target='_blank' href='http://localhost:60501/swagger-ui/index.html'>Swagger</a>, "
                +"<a target='_blank' href='http://localhost:60501/status'>Status</a>, "
                +"<a target='_blank' href='http://localhost:60501/h2-console'>H2Base</a>, "
                +"<a target='_blank' href='http://localhost:60501/starter'>Starter</a>"));
        list.addAll(coinDeskJsonProcessor.getAllCoinDeskRows());
        list.addAll(dogImageProcessor.getDogDetails());
        list.addAll(catFactProcessor.getCatFact());
        return list;
    }
}
