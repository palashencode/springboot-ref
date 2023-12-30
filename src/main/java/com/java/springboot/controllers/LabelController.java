package com.java.springboot.controllers;

import com.java.springboot.entities.Label;
import com.java.springboot.service.LabelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/labels")
public class LabelController {

    @Autowired
    LabelService labelService;

    @PostMapping
    public Label upsert(@RequestBody Label label) {
        return labelService.upsert(label.getKey(), label.getValue(), label.getLanguageCode());
    }

    @GetMapping("/key")
    public Label getByKey(@RequestParam(name = "key") String k,
                        @RequestParam(name = "lang") String lang
    ) {
        return labelService.getLabel(k, lang);
    }

    @GetMapping("/specific")
    public Label upsert(@RequestParam(name = "key") String k,
                        @RequestParam(name = "value") String v,
                        @RequestParam(name = "lang") String lang
    ) {
        return labelService.getLabel(k, v, lang);
    }

    @GetMapping()
    public List<Label> getLabels(@RequestParam(name = "lang", required = false) String lang) {
        if(StringUtils.isEmpty(lang)) {
            return labelService.getAll();
        }
        return labelService.getByLang(lang);
    }

}
