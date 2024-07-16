package com.ace.example.chapter07.facade.system;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class StreamingPlayer {

  Amplifier amplifier;
  String movie;

  public void on() {
    System.out.println("스트리밍 플레이어가 켜졌습니다.");
  }

  public void off() {
    System.out.println("스트리밍 플레이어가 꺼졌습니다.");
  }

  public void pause() {
    System.out.println("");
  }

  public void play(String movie) {
    System.out.println("스트리밍 플레이어에서 \"" + movie + "\"를 재생합니다.");
    this.movie = movie;
  }

  public void setSurroundAudio() {

  }

  public void setTwoChannelAudio() {

  }

  public void stop() {
    System.out.println("스트리밍 플레이어에서 \"" + movie + "\" 재생을 종료합니다.");
  }

  public String toString() {
    return null;
  }

}

