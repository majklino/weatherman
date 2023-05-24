package com.main.weatherman.routes;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.weatherman.services.CityService;
import com.main.weatherman.services.Measurer;
import com.main.weatherman.model.City;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {
    private final Measurer measurer;
    private final CityService cityService;

    @Autowired
    public CitiesController(Measurer measurer, CityService cityService){
        this.measurer = measurer;
        this.cityService = cityService;
    }

    @GetMapping()
    public List<City> getAllCities(){
        return cityService.getAllCities();
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
}
