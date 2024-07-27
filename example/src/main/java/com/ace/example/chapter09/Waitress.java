package com.ace.example.chapter09;

import com.ace.example.chapter09.iterator.Iterator;
import com.ace.example.chapter09.menu.DinerMenu;
import com.ace.example.chapter09.menu.PancakeHouseMenu;

public class Waitress {

  PancakeHouseMenu pancakeHouseMenu;
  DinerMenu dinerMenu;

  public Waitress(PancakeHouseMenu pancakeHouseMenu, DinerMenu dinerMenu) {
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
      var menuItem = iterator.next();
      System.out.print(menuItem.getName() + ", ");
      System.out.print(menuItem.getPrice() + " -- ");
      System.out.println(menuItem.getDescription());
    }
  }

}
