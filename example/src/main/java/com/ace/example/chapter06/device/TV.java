package com.ace.example.chapter06.device;

public class TV {
    String location;
    int channel;

    public TV(String location) {
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
        System.out.println(location + " TV가 켜졌습니다.");
    }

    public void off() {
        System.out.println(location + " TV가 꺼졌습니다.");
    }

    public void setInputChannel() {
        this.channel = 3;

        System.out.println(location + " TV에서 DVD를 재생합니다.");
    }
}
