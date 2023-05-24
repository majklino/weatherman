package com.main.weatherman.services;

import org.springframework.stereotype.Service;

import com.main.weatherman.model.Measurement;
import com.main.weatherman.repositories.MeasurementRepository;

@Service
public class MeasurementsService {
    private final MeasurementRepository repository;

    public MeasurementsService(MeasurementRepository repository){
        this.repository = repository;
    }

    public void addNewMeasurement(int cityId, long timestamp, double temp){
        this.repository.save(new Measurement(cityId, timestamp, temp));
    }

    public Object findMeasurementByCityId(int cityId){
        return this.repository.findAllByCityId(cityId);
    }
}
