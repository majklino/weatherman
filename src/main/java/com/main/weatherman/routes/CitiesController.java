package com.main.weatherman.routes;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.weatherman.services.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.main.weatherman.clients.ApiClient;
import com.main.weatherman.clients.CsvClient;
import com.main.weatherman.model.City;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {
    private final CityService service;

    @Autowired
    public CitiesController(CityService service){
        this.service = service;
    }

    @GetMapping()
    public List<City> getAllCities(){
        return service.getAllCities();
    }

    @GetMapping("/add")
    public Object addNewCity(){
        //return service.addNewCity("ImaginaryCity", (double)6.9, (double)9.6, "CZ");
        return 1;
    }

    @GetMapping("/measure/{cityname}")
    public Object measure(@PathVariable String cityname){
        ApiClient api = new ApiClient();
        CsvClient csv = new CsvClient();

        try {
            Object cityInfo = api.getCityInfoRequest(cityname);
            List<Object> cityList = (List<Object>)cityInfo;
            HashMap<String, Object> cityHash = (HashMap)cityList.get(0);
            System.out.println(cityHash);
            double lat = (double)cityHash.get("lat");
            double lon = (double)cityHash.get("lon");
            String countryCode = (String)cityHash.get("country");

            String countryName = csv.findCountryByCode(countryCode);

            service.measureForCity(cityname, lat, lon, countryCode, countryName);
            
            return "done";
        } catch (JsonMappingException e) {
            //TODO
            return e;
        } catch (JsonProcessingException e) {
            //TODO
            return e;
        } catch (FileNotFoundException e) {
            //TODO
            return e;
        } catch (IOException e) {
            //TODO
            return e;
        }

        //return "bruh";
    }

    @GetMapping("/name/{name}")
    public Object test(@PathVariable String name){
        ApiClient client = new ApiClient();
        
        try {
            return client.getCityInfoRequest(name);
        } catch (JsonMappingException e) {
            //TODO
            return e;
        } catch (JsonProcessingException e) {
            //TODO
            return e;
        }
    }

    @GetMapping("/weather/{lat}/{lon}")
    public Object test2(@PathVariable double lat, @PathVariable double lon){
        ApiClient client = new ApiClient();

        try {
            return client.getWeatherInfo(lat, lon);
        } catch (JsonMappingException e) {
            //TODO
            return e;
        } catch (JsonProcessingException e) {
            //TODO
            return e;
        }
    }

    @GetMapping("/csv/{code}")
    public Object test3(@PathVariable String code){
        CsvClient client = new CsvClient();
        
        try {
            return client.findCountryByCode(code);
        } catch (FileNotFoundException e) {
            // TODO 
            return e;
        } catch (IOException e) {
            // TODO 
            return e;
        }
    }
}
