package com.main.weatherman.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.main.weatherman.model.Measurement;

public interface MeasurementRepository extends MongoRepository<Measurement, String> {
    
    @Query("{ 'cityId' : ?0 }")
    List<Measurement> findAllByCityId(int cityId);
    
    @Query("{ 'cityId' : ?0 }")
    Optional<Measurement> findFirstByCityIdOrderByTimestampDesc(int cityId);
}
