package com.main.weatherman.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.main.weatherman.model.City;

@Service
public class Scheduler {

    private final CityService cityService;
    private final Measurer measurer;

    @Autowired
    public Scheduler(CityService cityService, Measurer measurer){
        this.cityService = cityService;
        this.measurer = measurer;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void executeTask() {
        System.out.println("Making an automatic measurements...");
        List<City> cities = cityService.getAllCities();
        for (City city : cities) {
            System.out.println(city.getName());
            try {
                measurer.measure(city, false);
            } catch (JsonMappingException e) {
                // TODO
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                // TODO
                e.printStackTrace();
            }
        }
    }
}
