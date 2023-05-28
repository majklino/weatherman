package com.main.weatherman.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.weatherman.repositories.CityRepository;
import com.main.weatherman.repositories.CountryRepository;
import com.main.weatherman.repositories.MeasurementRepository;

import jakarta.transaction.Transactional;

import com.main.weatherman.model.City;
import com.main.weatherman.model.Country;
import com.main.weatherman.model.Measurement;

@Service
@Transactional
public class CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final MeasurementRepository measurementRepository;

    @Autowired
    public CityService(CityRepository cityRepository, CountryRepository countryRepository, MeasurementRepository measurementRepository){
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.measurementRepository = measurementRepository;
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    public Measurement measureForCity(String cityName, double lat, double lon, String countryCode, String countryName, long timestamp, double temp, boolean manual){
        City city = this.addCity(cityName, lat, lon, countryCode, countryName);
        
        String type = "automatic";
        if(manual){
            type = "manual";
        }

        Measurement output = measurementRepository.save(new Measurement(city.getId(), timestamp, temp, type));
        return output;
    }

    public City addCity(String cityName, double lat, double lon, String countryCode, String countryName){
        City city = cityRepository.findByName(cityName);

        if(city == null){
            Country country = countryRepository.findByCode(countryCode);
            if(country == null){
                country = new Country();
                country.setCode(countryCode);
                country.setName(countryName);
                countryRepository.save(country);
            }
            city = new City();
            city.setName(cityName);
            city.setLat(lat);
            city.setLon(lon);
            city.setBelongsTo(country);
            cityRepository.save(city);

        }

        return city;
    }

    public void removeCity(String cityname){
        this.cityRepository.deleteByName(cityname);
    }
}
