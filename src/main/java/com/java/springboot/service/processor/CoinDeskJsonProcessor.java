package com.java.springboot.service.processor;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.springboot.clients.ProjectRestClient;
import com.java.springboot.model.RowData;
import com.java.springboot.model.jackson.coindesk.CoinDeskResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class CoinDeskJsonProcessor {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String COINDESK_PATH = "/v1/bpi/currentprice.json";

    @Value("${coindesk.api}")
    private String coindeskBasePath;

    @Autowired
    ProjectRestClient restClient; // = new RestTemplate();

    private String getCoinDeskResponseString(){
        return  restClient.quickGet(coindeskBasePath+COINDESK_PATH, String.class);
    }

    /**
     *  Jackson Parsing with POJO
     * @return
     */
    private CoinDeskResponse getCoinDeskResponse(){
        String res = getCoinDeskResponseString();
        CoinDeskResponse response = null;
        try {
            response = MAPPER.readValue(res, CoinDeskResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * Jackson Parsing
     * @return
     */
    public List<RowData> getCoinDeskResponseRowsDirectlyFromString(){
        Instant start = Instant.now();
        String json = getCoinDeskResponseString();
        Long millis = Duration.between(start,Instant.now()).toMillis();
        List<RowData> list = new ArrayList<>();

        JsonFactory factory = new JsonFactory();
        String coinName = "";
        String price = "";
        try {
            JsonNode rootNode = MAPPER.readTree(json);
            if(rootNode.isObject()){
                if(rootNode.has("chartName")){
                    coinName = rootNode.get("chartName").asText();
                }
                if(rootNode.has("bpi")){
                    Iterator<JsonNode> iterator = rootNode.get("bpi").elements();
                    while (iterator.hasNext()) {
                        JsonNode data = iterator.next();
                        price += (data.get("symbol").asText()+data.get("rate").asText()+"; ");
                    }
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        list.add(new RowData("Coindesk ("+millis+"ms)<br/>[Jackson Non-Pojo Parsing]", price));
        return list;
    }

    /**
     * Simple Json Parsing
     * @return
     */
    public List<RowData> getCoinDeskResponseRowsDirectlyFromStringWithSimpleJson(){
        Instant start = Instant.now();
        String json = getCoinDeskResponseString();
        Long millis = Duration.between(start,Instant.now()).toMillis();
        List<RowData> list = new ArrayList<>();

        String coinName = "";
        String price = "";
        JSONObject rootNode = new JSONObject(json);
        if(rootNode.has("chartName")){
            coinName = rootNode.get("chartName").toString();
        }
        if(rootNode.has("bpi")){
           JSONObject bpi = (JSONObject) rootNode.get("bpi");
           // Looping through keys
            Iterator iterator = bpi.keys();
            while(iterator.hasNext()){
                String key = (String)iterator.next();
                JSONObject currency = (JSONObject)bpi.get(key);
                price += (coinName+" "+currency.get("code").toString()+" "
                        +currency.get("symbol").toString()
                        +currency.get("rate").toString())+"; ";
            }
        }

        list.add(new RowData("Coindesk ("+millis+"ms)<br/>[Simple JSON Parsing]", price));
        return list;
    }

    public List<RowData> getCoinDeskResponseRows(){
        Instant start = Instant.now();
        CoinDeskResponse price = getCoinDeskResponse();
        Long millis = Duration.between(start,Instant.now()).toMillis();

        List<RowData> list = new ArrayList<>();
        list.add(new RowData("Coindesk ("+millis+"ms)<br/>[Jackson Pojo Parsing]",price.chartName+":"
                +price.bpi.get("USD").get("symbol")
                +price.bpi.get("USD").get("rate")));
        return list;
    }

    public List<RowData> getAllCoinDeskRows(){
        List<RowData> list = new ArrayList<>();
        list.addAll(getCoinDeskResponseRows());
        list.addAll(getCoinDeskResponseRowsDirectlyFromString());
        list.addAll(getCoinDeskResponseRowsDirectlyFromStringWithSimpleJson());
        return list;
    }

}
