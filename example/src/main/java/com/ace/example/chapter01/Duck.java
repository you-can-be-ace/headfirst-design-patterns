package com.ace.example.chapter01;

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

}
