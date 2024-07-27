package com.ace.example.chapter09;

import com.ace.example.chapter09.menu.MenuComponent;

public class Waitress {

  MenuComponent allMenus;

  public Waitress(MenuComponent allMenus) {
    this.allMenus = allMenus;
  }

  public void printMenu() {
    allMenus.print();
  }

}
