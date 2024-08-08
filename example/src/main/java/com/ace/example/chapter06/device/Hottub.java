package com.ace.example.chapter06.device;

public class Hottub {
    boolean on;
    int temperature;

    public Hottub() {}

    public void on() {
        on = true;
    }

    public void off() {
        on = false;
    }

    public void circulate() {
        if(on) {
            System.out.println("욕조에서 거품이 나옵니다.");
        }
    }

    public void jetsOn() {
        if(on) {
            System.out.println("욕조에서 공기가 나옵니다.");
        }
    }

    public void jetsOff() {
        if(on) {
            System.out.println("욕조에서 공기가 나오지 않습니다.");
        }
    }

    public void setTemperature(int temperature) {
        if(temperature > this.temperature) {
            System.out.println("욕조 온도를 " + temperature + "도로 설정합니다.");
            System.out.println("현재 욕도 온도: " + temperature + "도");
        } else {
            System.out.println("욕조 온도를 " + temperature + "도로 설정합니다.");
        }

        this.temperature = temperature;
    }
}
