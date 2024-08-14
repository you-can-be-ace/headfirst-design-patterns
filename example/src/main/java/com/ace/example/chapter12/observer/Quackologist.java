package com.ace.example.chapter12.observer;

public class Quackologist implements Observer {
    @Override
    public void update(QuackObservable duck) {
        System.out.println("꽥꽥학자: " + duck.toString() + " 가 방금 소리쳤다.");
    }
}
