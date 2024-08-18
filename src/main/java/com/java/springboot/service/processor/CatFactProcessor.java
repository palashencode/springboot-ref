package com.java.springboot.service.processor;

import com.java.springboot.clients.ProjectRestClient;
import com.java.springboot.model.RowData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CatFactProcessor {

    private static final String CAT_FACT_PATH = "/fact";

    @Value("${catfact.api}")
    private String catFactPath;

    @Autowired
    ProjectRestClient restClient;

    public List<RowData> getCatFact(){
        try {
            Map<String, String> catFact = restClient.quickGet(catFactPath+CAT_FACT_PATH, Map.class);
            Instant start = Instant.now();
            Long millis = Duration.between(start, Instant.now()).toMillis();
            return Arrays.asList(new RowData("Cat Fact ("+millis+"ms)", catFact.get("fact")));
        }catch (Exception e){
            return Arrays.asList(new RowData("Cat Fact (error)", e.getMessage()));
        }
    }
}
