package com.ace.example.chapter09.menu;

/**
 * leaf, node 역할이 달라 모든 메소드에 알맞는 기본 메소드를 구현하기 불가능하므로 자기 역할에 맞지 않는 상황을 기준으로 예외를 던지는 코드를 기본 구현으로 제공한다.
 */
public abstract class MenuComponent {

  public void add(MenuComponent menuComponent) {
    throw new UnsupportedOperationException();
  }

  public void remove(MenuComponent menuComponent) {
    throw new UnsupportedOperationException();
  }

  public MenuComponent getChild(int position) {
    throw new UnsupportedOperationException();
  }

  public String getName() {
    throw new UnsupportedOperationException();
  }

  public String getDescription() {
    throw new UnsupportedOperationException();
  }

  public double getPrice() {
    throw new UnsupportedOperationException();
  }

  public boolean isVegetarian() {
    throw new UnsupportedOperationException();
  }

  public void print() {
    throw new UnsupportedOperationException();
  }

}
