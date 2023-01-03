package com.example.mycropseller.models;

public class MonitorModel {
    String Temperature;
    String Humidity;


    public MonitorModel() {

    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }
}