package com.main.weatherman.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.weatherman.model.City;
import com.main.weatherman.model.Measurement;
import com.main.weatherman.repositories.MeasurementRepository;

@Service
public class CsvExportService {
    private final MeasurementRepository repository;
    private final CityService service;

    @Autowired
    public CsvExportService(MeasurementRepository repo, CityService service){
        this.repository = repo;
        this.service = service;
    }

    public void writeToCsv(String path) throws IOException {
        
        List<Measurement> measurements = this.repository.findAll();

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
            String row = "cityId,cityName,timestamp,temperature,type";
            bw.write(row);
            bw.newLine();

            for (Measurement measurement : measurements) {
                City city = this.service.getCityById(measurement.getCityId());
                if(city == null){ continue; }
                row = measurement.getCityId() + "," + city.getName() + "," + measurement.getTimestamp() + "," + measurement.getTemp() + "," + measurement.getType();
                bw.write(row);
                bw.newLine();
            }
        }
    }
    
}
