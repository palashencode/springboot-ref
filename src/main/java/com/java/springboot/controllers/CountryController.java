package com.java.springboot.controllers;

import com.java.springboot.entities.Country;
import com.java.springboot.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping
    public List<Country> getAll(){
        return countryService.getCountries();
    }

    @GetMapping("/{name}")
    public Country getCountry(String name){
        return countryService.getCountry(name);
    }

    @DeleteMapping("/{name}")
    public String deleteCountry(String name){
        countryService.deleteCountry(name);
        return "ok";
    }

}
