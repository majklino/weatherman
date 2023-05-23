package com.main.weatherman.routes;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.weatherman.services.CityService;
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
        return service.addNewCity("ImaginaryCity", (float)6.9, (float)9.6, "CZ");
    }
}
