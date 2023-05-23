package com.main.weatherman.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.weatherman.model.Country;
import com.main.weatherman.repositories.CountryRepository;

@Service
public class CountryService {
    private final CountryRepository repository;

    @Autowired
    public CountryService(CountryRepository repository){
        this.repository = repository;
    }

    public List<Country> getAllCountries(){
        return repository.findAll();
    }

    // public Country getCountryByName(String name){
    //     return repository.findByName(name);
    // }
}

