package com.main.weatherman.routes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.weatherman.model.Country;
import com.main.weatherman.services.CountryService;

@RestController
@RequestMapping("/api/countries")
public class CountriesController {
    private final CountryService service;

    @Autowired
    public CountriesController(CountryService service){
        this.service = service;
    }

    @GetMapping()
    public List<Country> getAllCountries(){
        return service.getAllCountries();
    }

    // @GetMapping("/{name}")
    // public Country getCountryById(@PathVariable String name){
    //     return service.getCountryByName(name);
    // }
}
