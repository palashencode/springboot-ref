package com.java.springboot.service;

import com.java.springboot.entities.Label;

import java.util.List;

public interface LabelService {

    List<Label> getAll();
    List<Label> getByLang(String lang);
    void deleteAll();
    List<Label> saveAll(List<Label> labels);
    Label upsert(String k, String v, String lang);
    Label getLabel(String k, String v, String lang);
    Label getLabel(String k, String lang);
    void evictAllCache();
}
