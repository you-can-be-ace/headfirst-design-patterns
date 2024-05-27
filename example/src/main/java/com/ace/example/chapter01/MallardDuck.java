package com.ace.example.chapter01;

public class MallardDuck extends Duck {

  public MallardDuck() {
    quackBehaior = new Quack();
    flyBehavior = new FlyWithWings();
  }

  public void display() {
    System.out.print("저는 물오리입니다");
  }

}
