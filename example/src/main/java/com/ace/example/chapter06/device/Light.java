package com.ace.example.chapter06.device;

public class Light {
    String location;

    public Light(String location) {
        switch(location) {
            case "Living Room":
                this.location = "거실";
                break;
            case "Kitchen":
                this.location = "주방";
                break;
            case "Garage":
                this.location = "차고";
                break;
        }
    }

    public void on() {
        System.out.println(location + " 조명이 켜졌습니다.");
    }

    public void off() {
        System.out.println(location + " 조명이 꺼졌습니다.");
    }
}
