package com.java.springboot.service.impl;

import com.java.springboot.entities.Label;
import com.java.springboot.repository.LabelRepository;
import com.java.springboot.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
public class LabelServiceImpl implements LabelService {

    @Autowired
    LabelRepository labelRepository;

    @Override
    @Cacheable(value = "LABEL_CACHE_ALL")
    public List<Label> getAll() {
        return labelRepository.findAll();
    }

    @Override
    public Label getLabel(String k, String v, String lang){
        return labelRepository.findByKeyAndValueAndLanguageCode(k, v, lang);
    }

    @Override
    public Label getLabel(String k, String lang){
        return labelRepository.findByKeyAndLanguageCode(k, lang);
    }



    @Override
    public Label upsert(String k, String v, String lang){
        Label label = labelRepository.findByKeyAndLanguageCode(k, lang);
        if(ObjectUtils.isEmpty(label)){
            // insert
            return logReturn("New Label Inserted with Value ", labelRepository.save(Label.builder()
                                                                                    .key(k)
                                                                                    .value(v)
                                                                                    .languageCode(lang)
                                                                                    .build()));
        }
        label.setValue(v);
        return logReturn("Label Updated with Value ", labelRepository.save(label));
    }

    private Label logReturn(String msg, Label label){
        log.info(msg+label.toString());
        return label;
    }

    @Override
    @Cacheable(value = "LABEL_CACHE_LANG")
    public List<Label> getByLang(String lang) {
        return labelRepository.findByLanguageCode(lang);
    }

    @Override
    public void deleteAll() {
        labelRepository.deleteAll();;
    }

    @Override
    public List<Label> saveAll(List<Label> labels) {
        return labelRepository.saveAll(labels);
    }

    @CacheEvict(value = {"LABEL_CACHE_ALL", "LABEL_CACHE_LANG"}, allEntries = true)
    public void evictAllCache(){
        log.info("Evicting caches, LABEL_CACHE_LANG and LABEL_CACHE_ALL at:"+ Instant.now());
    }
}
