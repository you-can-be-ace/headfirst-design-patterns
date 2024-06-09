package com.ace.example.chapter02.weather.display;

import com.ace.example.chapter02.weather.WeatherData;
import com.ace.example.chapter02.weather.observer.Observer;

public class StatisticsDisplay implements Observer, DisplayElement {

	private float maxTemp = 0.0f;
	private float minTemp = 200;
	private float tempSum= 0.0f;
	private int numReadings;
	private WeatherData weatherData;

	public StatisticsDisplay(WeatherData weatherData) {
		this.weatherData = weatherData;

		weatherData.registerObserver(this);
	}

	@Override
	public void update() {
		float temp = weatherData.getTemperature();
		tempSum += temp;
		numReadings++;

		if (temp > maxTemp) {
			maxTemp = temp;
		}
 
		if (temp < minTemp) {
			minTemp = temp;
		}

		display();
	}

	@Override
	public void display() {
		System.out.println("평균/최대/최저 온도 = " + (tempSum / numReadings)
			+ "/" + maxTemp + "/" + minTemp);
	}
}
