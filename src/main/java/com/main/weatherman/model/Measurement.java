package com.main.weatherman.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "measurements")
public class Measurement {
    
    @Id
    private String id;

    private int cityId;

    private long timestamp;

    private double temp;

    private String type;

    public Measurement(int cityId, long timestamp, double temp, String type){
        this.cityId = cityId;
        this.timestamp = timestamp;
        this.temp = temp;
        this.type = type;
    }

    public int getCityId(){
        return this.cityId;
    }

    public void setCityId(int cityId){
        this.cityId = cityId;
    }

    public long getTimestamp(){
        return this.timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public double getTemp(){
        return this.temp;
    }

    public void setTemp(double temp){
        this.temp = temp;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }


}
