package com.java.springboot.model.jackson.coindesk;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CoinDeskResponse{
    public Time time;
    public String disclaimer;
    public String chartName;
    public Map<String, Map<String, Object>> bpi = new LinkedHashMap<>();
    @JsonAnySetter
    void setBpi(String key, Map<String, Object> value) {
        bpi.put(key, value);
    }
}