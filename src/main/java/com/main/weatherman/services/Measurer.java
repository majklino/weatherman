package com.main.weatherman.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.main.weatherman.clients.ApiClient;
import com.main.weatherman.clients.CsvClient;
import com.main.weatherman.model.City;
import com.main.weatherman.model.Measurement;

@Service
public class Measurer {

    private final CityService cityService;
    private final ApiClient apiClient;
    private final CsvClient csvClient;

    @Autowired
    public Measurer(CityService service, ApiClient apiClient, CsvClient csvCLient) {
        this.cityService = service;
        this.apiClient = apiClient;
        this.csvClient = csvCLient;
    }

    public Measurement measureNew(String cityname, boolean manual) throws FileNotFoundException, IOException {

        Object cityInfoObj = apiClient.getCityInfoRequest(cityname);
        HashMap<String, Object> cityInfo = (HashMap<String, Object>) ((List<Object>) cityInfoObj).get(0);
        System.out.println(cityInfo);
        double lat = (double) cityInfo.get("lat");
        double lon = (double) cityInfo.get("lon");
        String countryCode = (String) cityInfo.get("country");

        String countryName = csvClient.findCountryByCode(countryCode);

        long timestamp = System.currentTimeMillis() / 1000L;

        Object weatherInfoObj = apiClient.getWeatherInfo(lat, lon);
        HashMap<String, Object> weatherInfo = (HashMap<String, Object>) weatherInfoObj;
        System.out.println(weatherInfo);
        double temp = (double) ((HashMap<String, Object>) weatherInfo.get("main")).get("temp");

        Measurement meas = cityService.measureForCity(cityname, lat, lon, countryCode, countryName, timestamp, temp,
                true);

        return meas;
    }

    public Measurement measure(City city, boolean manual) throws JsonMappingException, JsonProcessingException{
        long timestamp = System.currentTimeMillis() / 1000L;

        Object weatherInfoObj = apiClient.getWeatherInfo(city.getLat(), city.getLon());
        HashMap<String, Object> weatherInfo = (HashMap<String, Object>) weatherInfoObj;
        System.out.println(weatherInfo);
        double temp = (double) ((HashMap<String, Object>) weatherInfo.get("main")).get("temp");

        Measurement meas = cityService.measureForCity(city.getName(), city.getLat(), city.getLon(), city.getBelongsTo().getCode(), city.getBelongsTo().getName(), timestamp, temp, manual);
        return meas;
    }
}
