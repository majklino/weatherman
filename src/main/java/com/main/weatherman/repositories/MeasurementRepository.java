package com.main.weatherman.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.main.weatherman.model.Measurement;

public interface MeasurementRepository extends MongoRepository<Measurement, String> {
    
    List<Measurement> findAllByCityId(int cityId);
    /*
    @Query("{name:'?0'}")
    GroceryItem findItemByName(String name);
    
    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<GroceryItem> findAll(String category);
    
    public long count();
     */
}
