package com.java.springboot.controllers;


import com.java.springboot.entities.Label;
import com.java.springboot.entities.User;
import com.java.springboot.service.CountryService;
import com.java.springboot.service.LabelService;
import com.java.springboot.service.UserService;
import com.java.springboot.util.CustomCSVColumnWithHeaderMappingStrategy;
import com.java.springboot.util.GeneralUtil;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/csv/")
@Slf4j
public class CSVController {

    private static final String csvPath = "src/main/resources/csv/";

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @Autowired
    LabelService labelService;



    @PostMapping(value = "upload/labels", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,Object> uploadLabelsInCSV(@RequestPart(required = true) MultipartFile file, boolean overWrite){
        Map<String,Object> response = new LinkedHashMap<>();
        List<Label> labels = null;
        response.put("fileName", file.getName());
        response.put("fileNameOriginal", file.getOriginalFilename());
        response.put("fileSize", file.getSize()+"");
        response.put("fileType", file.getContentType());
        try(Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)){
            CsvToBean<Label> csvReader = new CsvToBeanBuilder<Label>(reader)
                    .withType(Label.class)
                    .build();
            labels = csvReader.parse();
            response.put("data", labels);
        }catch(Exception e){
            log.error("error in calling - readUsersFromCSVFolder"+e.getMessage());
        }

        if(overWrite){
            labelService.deleteAll();
            List<Label> labelsDB = labelService.saveAll(labels);
            response.put("data", labelsDB);
        }
        return response;
    }

    @GetMapping("download/labels")
    public void  downloadLabelsInCSV(HttpServletResponse response){

        // set response headers
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "Labels.csv" + "\"");
        response.setCharacterEncoding("UTF-8");

        List<Label> labels = labelService.getAll();
        String[] header = new String[] {"id", "key", "value", "languageCode"};
        try{
            generateAndWriteCSV(labels, response.getWriter(), header, Label.class);
        }catch(Exception e){
            log.error("error in calling - downloadLabelsInCSV"+e.getMessage());
        }
    }

    @GetMapping("download/users")
    public void  downloadUsersInCSV(HttpServletResponse response){

        // set response headers
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "Users.csv" + "\"");
        response.setCharacterEncoding("UTF-8");

        List<User> users = userService.getUsers();
        String[] header = new String[] {"name", "userName", "city", "dateOfJoining", "exactDob" , "description", "status"};
        try{
            generateAndWriteCSV(users, response.getWriter(), header, User.class);
        }catch(Exception e){
            log.error("error in calling - saveUsersToCSVFolder"+e.getMessage());
        }
    }

    @GetMapping("file/users")
    public void  saveUsersToCSVFolder(){
        List<User> users = userService.getUsers();
        String[] header = new String[] {"name", "userName", "city", "dateOfJoining", "exactDob" , "description", "status"};

        try(Writer writer = new FileWriter(csvPath+"Users.csv", StandardCharsets.UTF_8)){
            generateAndWriteCSV(users, writer, header, User.class);
        }catch(Exception e){
            log.error("error in calling - saveUsersToCSVFolder"+e.getMessage());
        }
    }

    @GetMapping("read/users")
    @Operation(summary = "this will read a bunch of user data from csv folder and return")
    public ResponseEntity<List<User>> readUsersFromCSVFolder(){
        List<User> users = null;
        try(Reader reader = new FileReader(csvPath+"Users.csv", StandardCharsets.UTF_8)){
            CsvToBean<User> csvReader = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .build();
            users = csvReader.parse();
        }catch(Exception e){
            log.error("error in calling - readUsersFromCSVFolder"+e.getMessage());
        }
        return ResponseEntity.ok(users);
    }

    private <T> void generateAndWriteCSV(List<T> list, Writer writer, String[] header, Class<T> clazz) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        CustomCSVColumnWithHeaderMappingStrategy<T> strategy = new CustomCSVColumnWithHeaderMappingStrategy<>(GeneralUtil.toUpper(header));
        strategy.setType(clazz);
        strategy.setColumnMapping(header);

        StatefulBeanToCsv beanToCSV = new StatefulBeanToCsvBuilder<T>(writer)
                .withMappingStrategy(strategy)
                .build();
        beanToCSV.write(list);
    }

}
