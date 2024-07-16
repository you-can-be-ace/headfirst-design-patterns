package com.ace.example.chapter07.facade.system;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Amplifier {

  Tuner tuner;
  StreamingPlayer player;

  public void on() {
    System.out.println("앰프가 켜졌습니다.");
  }

  public void off() {
    System.out.println("앰프가 꺼졌습니다.");
  }

  public void setStreamingPlayer(StreamingPlayer player) {
    System.out.println("앰프를 스트리밍 플레이어와 연결합니다.");
    this.player = player;
  }

  public void setStereoSound() {

  }

  public void setSurroundSound() {
    System.out.println("앰프를 서라운드 모드로 설정합니다. (5.1 채널)");
  }

  public void setTuner() {

  }

  public void setVolume(Integer volume) {
    System.out.println("앰프 볼륨을 " + volume + "로 설정합니다.");
  }

  public String toString() {
    return null;
  }

}
