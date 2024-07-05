package com.ace.example.chapter06.device;

public class CeilingFan {
    public static final int HIGH = 3;
    public static final int MEDIUM = 2;
    public static final int LOW = 1;
    public static final int OFF = 0;
    String location;
    int speed;

    public CeilingFan(String location) {
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

        speed = OFF;
    }

    public void high() {
        speed = HIGH;

        System.out.println(location + " 선풍기 속도가 HIGH로 설정되었습니다.");
    }

    public void medium() {
        speed = MEDIUM;

        System.out.println(location + " 선풍기 속도가 MEDIUM으로 설정되었습니다.");
    }

    public void low() {
        speed = LOW;

        System.out.println(location + " 선풍기 속도가 LOW로 설정되었습니다.");
    }

    public void off() {
        speed = OFF;

        System.out.println(location + " 선풍기가 꺼졌습니다.");
    }

    public int getSpeed() {
        return speed;
    }
}
