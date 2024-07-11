package com.ace.example.chapter07.facade.system;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Projector {

  StreamingPlayer player;

  public void on() {
    System.out.println("프로젝터가 켜졌습니다.");
  }

  public void off() {
    System.out.println("프로젝터가 꺼졌습니다.");
  }

  public void wideScreenMode() {
    System.out.println("프로젝터 화면 비율을 와이드 모드로 설정합니다. (16:9 비율)");
  }

  public String toString() {
    return null;
  }

}
