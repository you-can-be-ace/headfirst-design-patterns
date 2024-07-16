package com.ace.example.chapter07.facade.system;

public class TheaterLights {

  public void on() {
    System.out.println("조명이 켜졌습니다.");
  }

  public void off() {
    System.out.println("조명이 꺼졌습니다.");
  }

  public void dim(Integer percent) {
    System.out.println("조명 밝기를 " + percent + "%로 설정합니다.");
  }

  public String toString() {
    return null;
  }

}
