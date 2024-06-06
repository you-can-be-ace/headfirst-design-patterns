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
    public void update() {
        this.temperature = weatherData.getTemperature();
        this.humidity = weatherData.getHumidity();
        display();
    }

    @Override
    public void display() {
        System.out.println("현재 사태 : 온도 " + temperature + "F, 습도 " + humidity + "%");
    }
}
