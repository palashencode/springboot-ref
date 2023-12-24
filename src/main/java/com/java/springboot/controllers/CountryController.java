package com.java.springboot.controllers;

import com.java.springboot.entities.Country;
import com.java.springboot.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Country getCountry(@PathVariable  String name){
        return countryService.getCountry(name);
    }

    @GetMapping("/err")
    public Country getCountryError(){
        throw new RuntimeException("This is a custom error, thrown by the application.");
    }

    @DeleteMapping("/{name}")
    public String deleteCountry(String name){
        countryService.deleteCountry(name);
        return "ok";
    }

}
