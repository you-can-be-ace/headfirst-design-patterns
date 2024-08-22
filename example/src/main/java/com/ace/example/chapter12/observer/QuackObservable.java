package com.ace.example.chapter12.observer;


public interface QuackObservable {

    public void registerObserver(Observer observer);
    public void notifyObservers();
}
