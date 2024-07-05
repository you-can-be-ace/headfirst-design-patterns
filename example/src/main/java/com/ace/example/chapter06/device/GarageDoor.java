package com.ace.example.chapter06.device;

public class GarageDoor {
    String location;

    public GarageDoor(String location) {
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

    public void up() {
        System.out.println(location + " 문이 열렸습니다.");
    }

    public void down() {
        System.out.println(location + " 문이 닫혔습니다.");
    }

    public void stop() {
        System.out.println(location + " 문이 멈췄습니다.");
    }

    public void lightOn() {
        System.out.println(location + " 불이 켜졌습니다.");
    }

    public void lightOff() {
        System.out.println(location + " 불이 꺼졌습니다.");
    }
}
