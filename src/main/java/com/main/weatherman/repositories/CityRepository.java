package com.main.weatherman.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.weatherman.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    City findByName(String name);
    void deleteByName(String name);
    City findById(int id);
}
