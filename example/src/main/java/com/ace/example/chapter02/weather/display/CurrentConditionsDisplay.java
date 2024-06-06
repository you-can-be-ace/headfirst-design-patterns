package com.ace.example.chapter02.weather.display;

import com.ace.example.chapter02.weather.WeatherData;
import com.ace.example.chapter02.weather.observer.Observer;

public class CurrentConditionsDisplay implements Observer, DisplayElement {

    private float temperature;
    private float humidity;
    private WeatherData weatherData;

    public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;

        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    @Override
    public void display() {
        System.out.println("현재 사태 : 온도 " + temperature + "F, 습도 " + humidity + "%");
    }
}
