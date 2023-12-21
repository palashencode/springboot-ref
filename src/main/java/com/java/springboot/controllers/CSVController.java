package com.java.springboot.controllers;


import com.java.springboot.entities.User;
import com.java.springboot.service.CountryService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/csv/")
@Slf4j
public class CSVController {

    private final static String csvPath = "src/main/resources/csv/";

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

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
