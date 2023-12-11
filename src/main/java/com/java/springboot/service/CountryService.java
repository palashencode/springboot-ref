package com.java.springboot.service;

import com.java.springboot.entities.Country;

import java.util.List;

public interface CountryService {

    Country createCountry(String name);

    public Country getCountry(String name);

    List<Country> getCountries();

    void deleteCountry(String name);

}
