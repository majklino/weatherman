package com.main.weatherman.routes;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.weatherman.services.CityService;
import com.main.weatherman.services.MeasurementsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.main.weatherman.clients.ApiClient;
import com.main.weatherman.clients.CsvClient;
import com.main.weatherman.model.City;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {
    private final CityService cityService;

    @Autowired
    public CitiesController(CityService service){
        this.cityService = service;
    }

    @GetMapping()
    public List<City> getAllCities(){
        return cityService.getAllCities();
    }

    @GetMapping("/measure/{cityname}")
    public Object measure(@PathVariable String cityname){
        ApiClient api = new ApiClient();
        CsvClient csv = new CsvClient();

        try {
            Object cityInfoObj = api.getCityInfoRequest(cityname);
            HashMap<String, Object> cityInfo = (HashMap<String, Object>)((List<Object>)cityInfoObj).get(0);
            System.out.println(cityInfo);
            double lat = (double)cityInfo.get("lat");
            double lon = (double)cityInfo.get("lon");
            String countryCode = (String)cityInfo.get("country");

            String countryName = csv.findCountryByCode(countryCode);

            long timestamp = System.currentTimeMillis() / 1000L;

            Object weatherInfoObj = api.getWeatherInfo(lat, lon);
            HashMap<String, Object> weatherInfo = (HashMap<String, Object>)weatherInfoObj;
            System.out.println(weatherInfo);
            double temp =  (double)((HashMap<String, Object>)weatherInfo.get("main")).get("temp");
            
            cityService.measureForCity(cityname, lat, lon, countryCode, countryName, timestamp, temp);
            
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
}
