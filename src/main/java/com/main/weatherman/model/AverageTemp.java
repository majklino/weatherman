package com.main.weatherman.model;

public class AverageTemp {
    public AverageTemp(int cityId, double avg1, double avg7, double avg14){
        this.cityId = cityId;
        this.avg1 = avg1;
        this.avg7 = avg7;
        this.avg14 = avg14;
    }

    private int cityId;

    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    private double avg1;
    
    public double getAvg1() {
        return avg1;
    }
    public void setAvg1(double avg1) {
        this.avg1 = avg1;
    }

    private double avg7;
    
    public double getAvg7() {
        return avg7;
    }
    public void setAvg7(double avg7) {
        this.avg7 = avg7;
    }

    private double avg14;

    
    public double getAvg14() {
        return avg14;
    }
    public void setAvg14(double avg14) {
        this.avg14 = avg14;
    }
}
