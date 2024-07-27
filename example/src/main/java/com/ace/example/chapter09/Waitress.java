package com.ace.example.chapter09;

import com.ace.example.chapter09.menu.Menu;
import com.ace.example.chapter09.menu.MenuItem;
import java.util.Iterator;

public class Waitress {

  Menu pancakeHouseMenu;
  Menu dinerMenu;

  public Waitress(Menu pancakeHouseMenu, Menu dinerMenu) {
    this.pancakeHouseMenu = pancakeHouseMenu;
    this.dinerMenu = dinerMenu;
  }

  public void printMenu() {
    var pancakeIterator = pancakeHouseMenu.createIterator();
    var dinerIterator = dinerMenu.createIterator();

    System.out.println("메뉴\n---\n아침 메뉴");
    printMenu(pancakeIterator);
    System.out.println("\n점심 메뉴");
    printMenu(dinerIterator);
  }

  private void printMenu(Iterator iterator) {
    while (iterator.hasNext()) {
      var menuItem = (MenuItem) iterator.next();
      System.out.print(menuItem.getName() + ", ");
      System.out.print(menuItem.getPrice() + " -- ");
      System.out.println(menuItem.getDescription());
    }
  }

}
