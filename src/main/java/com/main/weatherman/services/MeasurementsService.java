package com.main.weatherman.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.main.weatherman.model.Measurement;
import com.main.weatherman.repositories.MeasurementRepository;

@Service
public class MeasurementsService {
    private final MeasurementRepository repository;

    public MeasurementsService(MeasurementRepository repository) {
        this.repository = repository;
    }

    public void addNewMeasurement(int cityId, long timestamp, double temp, boolean manual) {
        String type = "automatic";
        if (manual) {
            type = "manual";
        }
        this.repository.save(new Measurement(cityId, timestamp, temp, type));
    }

    public List<Measurement> findMeasurementsByCityId(int cityId) {
        return this.repository.findAllByCityId(cityId);
    }

    public double findAvgTempForCity(int cityId, int noOfPastDays) {
        List<Measurement> measurements = this.findMeasurementsByCityId(cityId);
        double sum = 0;
        double count = 0;
        for (Measurement measurement : measurements) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime past = now.minus(noOfPastDays, ChronoUnit.DAYS);
            ZonedDateTime zdt = ZonedDateTime.of(past, ZoneId.systemDefault());
            long pastTimestamp = zdt.toInstant().toEpochMilli() / 1000L;

            if(measurement.getTimestamp() > pastTimestamp){
                sum += measurement.getTemp();
                count += 1;
            }
        }
        double avg = 0;
        if(count > 0){
            avg = sum / count;
        }
        return avg;
    }
}
