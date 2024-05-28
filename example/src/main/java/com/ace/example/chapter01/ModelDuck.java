package com.ace.example.chapter01;

import com.ace.example.chapter01.fly.FlyNoWay;
import com.ace.example.chapter01.quack.Quack;

public class ModelDuck extends Duck {

  public ModelDuck() {
    flyBehavior = new FlyNoWay();
    quackBehaior = new Quack();
  }

  public void display() {
    System.out.print("저는 모형 오리입니다");
  }

}
