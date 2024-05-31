package com.ace.example.chapter01;

import com.ace.example.chapter01.fly.FlyWithWings;
import com.ace.example.chapter01.quack.Quack;

public class MallardDuck extends Duck {

  public MallardDuck() {
    flyBehavior = new FlyWithWings();
    quackBehaior = new Quack();
  }

  public void display() {
    System.out.print("저는 물오리입니다");
  }

}
