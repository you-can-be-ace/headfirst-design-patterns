package com.ace.example.chapter06.device;

public class Stereo {
    String location;

    public Stereo(String location) {
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
        System.out.println(location + " 오디오가 켜졌습니다.");
    }

    public void off() {
        System.out.println(location + " 오디오가 꺼졌습니다.");
    }

    public void setCd() {
        System.out.println(location + " 오디오에서 CD가 재생됩니다.");
    }

    public void setDvd() {
        System.out.println(location + " 오디오에서 DVD가 재생됩니다.");
    }

    public void setRadio() {
        System.out.println(location + " 오디오에서 라디오가 재생됩니다.");
    }

    public void setVolume(int volume) {
        System.out.println(location + " 오디오의 볼륨이 " + volume + "로 설정되었습니다.");
    }
}
