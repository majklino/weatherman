package com.main.weatherman.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.weatherman.repositories.CityRepository;
import com.main.weatherman.repositories.CountryRepository;
import com.main.weatherman.model.City;
import com.main.weatherman.model.Country;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public CityService(CityRepository cityRepository, CountryRepository countryRepository){
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    public City addNewCity(String name, float lat, float lon, String countryCode){
        Country country = countryRepository.findByCode(countryCode);

        City city = new City();
        city.setName(name);
        city.setLat(lat);
        city.setLon(lon);
        city.setBelongsTo(country);
        
        return cityRepository.save(city);
    }
}
