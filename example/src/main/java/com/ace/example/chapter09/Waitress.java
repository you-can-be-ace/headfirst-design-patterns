package com.ace.example.chapter09;

import com.ace.example.chapter09.menu.Menu;
import com.ace.example.chapter09.menu.MenuItem;
import java.util.Iterator;
import java.util.List;

public class Waitress {

  List<Menu> menus;

  public Waitress(List<Menu> menus) {
    this.menus = menus;
  }

  public void printMenu() {
    var menuIterator = menus.iterator();
    while (menuIterator.hasNext()) {
      var menu = menuIterator.next();
      printMenu(menu.createIterator());
    }
  }

  private void printMenu(Iterator<MenuItem> iterator) {
    while (iterator.hasNext()) {
      var menuItem = iterator.next();
      System.out.print(menuItem.getName() + ", ");
      System.out.print(menuItem.getPrice() + " -- ");
      System.out.println(menuItem.getDescription());
    }
  }

}
