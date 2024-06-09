package com.ace.example.chapter02.weather.subject;

import com.ace.example.chapter02.weather.observer.Observer;

public interface Subject {

    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
