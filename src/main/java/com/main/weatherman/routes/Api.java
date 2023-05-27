package com.main.weatherman.routes;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.weatherman.services.CityService;
import com.main.weatherman.services.CountryService;
import com.main.weatherman.services.Measurer;

@RestController
@RequestMapping("/api")
public class Api {
    private final CountryService countryService;
    private final CityService cityService;
    private final Measurer measurer;

    @Autowired
    public Api(CountryService c1, CityService c2, Measurer m2){
        this.countryService = c1;
        this.cityService = c2;
        this.measurer = m2;
    }

    @GetMapping("/country/all")
    public Object getAllCountries(){
        return this.countryService.getAllCountries();
    }

    @GetMapping("/city/all")
    public Object getAllCities(){
        return this.cityService.getAllCities();
    }

    @GetMapping("/measure/{cityname}")
    public Object measure(@PathVariable String cityname){
        try {
            return this.measurer.measureNew(cityname, true);
        } catch (FileNotFoundException e) {
            // TODO
            return e;
        } catch (IOException e) {
            // TODO
            return e;
        }
    }

    @GetMapping("/city/add/{cityname}")
    public Object addCity(@PathVariable String cityname){
        try {
            return this.measurer.addCity(cityname);
        } catch (FileNotFoundException e) {
            // TODO
            return e;
        } catch (IOException e) {
            // TODO
            return e;
        }
    }

    @GetMapping("/city/remove/{cityname}")
    public String removeCity(@PathVariable String cityname){
        this.cityService.removeCity(cityname);
        return cityname + " was deleted.";
    }

    // @GetMapping("/country/add/{code}")
    // public Object addCountry(@PathVariable String code){
    //     try {
    //         return this.measurer.addCountry(code);
    //     } catch (FileNotFoundException e) {
    //         // TODO
    //         return e;
    //     } catch (IOException e) {
    //         // TODO
    //         return e;
    //     }
    // }
    
}
