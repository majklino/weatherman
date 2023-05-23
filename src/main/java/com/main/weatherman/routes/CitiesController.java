package com.main.weatherman.routes;


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
        //return service.addNewCity("ImaginaryCity", (float)6.9, (float)9.6, "CZ");
        return 1;
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
    public Object test2(@PathVariable float lat, @PathVariable float lon){
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
}
