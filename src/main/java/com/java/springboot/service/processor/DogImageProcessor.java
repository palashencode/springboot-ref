package com.java.springboot.service.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.springboot.clients.ProjectRestClient;
import com.java.springboot.model.RowData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DogImageProcessor {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String DOG_IMAGE_PATH = "/api/breeds/image/random";

    @Value("${dogimage.api}")
    private String dogCeoPath;

    @Autowired
    ProjectRestClient restClient;

    public List<RowData> getDogDetails(){

        try {
        Instant start = Instant.now();
        Map<String, String> dogImage = restClient.quickGet(dogCeoPath+DOG_IMAGE_PATH, Map.class);
        Long millis = Duration.between(start, Instant.now()).toMillis();
        return Arrays.asList(new RowData("Dog Image ("+millis+"ms)", wrapImg(dogImage.get("message"))));
        }catch (Exception e){
            return Arrays.asList(new RowData("Dog Image (error)", e.getMessage()));
        }
    }

    private String wrapImg(String path){
        return "<img width='200px' src='"+path+"'/>";
    }

}
