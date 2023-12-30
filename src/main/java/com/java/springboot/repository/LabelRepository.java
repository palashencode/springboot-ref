package com.java.springboot.repository;

import com.java.springboot.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LabelRepository extends CrudRepository<Label, Integer>, JpaRepository<Label, Integer> {

    List<Label> findByLanguageCode(String lang);
    Label findByKeyAndValueAndLanguageCode(String k, String v, String lang);
    Label findByKeyAndLanguageCode(String k, String lang);
}
