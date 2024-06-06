package com.ace.example.chapter02.weather.observer;

public interface Observer {

    void update(float temperature, float humidity, float pressure);
}
