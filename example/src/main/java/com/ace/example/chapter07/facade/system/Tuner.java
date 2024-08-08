package com.ace.example.chapter07.facade.system;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Tuner {

  Amplifier amplifier;

  public void on() {
    System.out.println("");
  }

  public void off() {
    System.out.println("");
  }

  public void setAm() {

  }

  public void setFm() {

  }

  public void setFrequency() {

  }

  public String toString() {
    return null;
  }

}
