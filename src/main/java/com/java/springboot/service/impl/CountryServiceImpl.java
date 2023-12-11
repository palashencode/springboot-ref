package com.java.springboot.service.impl;

import com.java.springboot.entities.Country;
import com.java.springboot.repository.CountryRepository;
import com.java.springboot.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country createCountry(String name) {
        Country c = new Country();
        c.setName(name);
        return countryRepository.save(c);
    }

    @Override
    public Country getCountry(String name) {
        List<Country> countryList = countryRepository.findByNameIgnoreCase(name);
        if ( countryList != null && countryList.size() > 0)
            return countryList.get(0);
        return null;
    }

    @Override
    public List<Country> getCountries() {
        List<Country> countryList = new ArrayList<>();
        Iterable<Country> countryIterable = countryRepository.findAll();
        for (Country c : countryIterable ){
            countryList.add(c);
        }
        return countryList;
    }

    @Override
    @Transactional
    public void deleteCountry(String name) {
        countryRepository.deleteByCountryName(name);
    }
}
