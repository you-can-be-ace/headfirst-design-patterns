package com.ace.example.chapter01;

import com.ace.example.chapter01.fly.FlyBehavior;
import com.ace.example.chapter01.quack.QuackBehavior;

public abstract class Duck {

  FlyBehavior flyBehavior;
  QuackBehavior quackBehaior;

  public Duck() {

  }

  public abstract void display();

  public void performFly() {
    flyBehavior.fly();
  }

  public void performQuack() {
    quackBehaior.quack();
  }

  public void swim() {
    System.out.println("모든 오리는 물에 뜹니다. 가짜 오리도 뜨죠");
  }

  public void setFlyBehavior(FlyBehavior fb) {
    flyBehavior = fb;
  }

  public void setQuackBehavior(QuackBehavior qb) {
    quackBehaior = qb;
  }

}
