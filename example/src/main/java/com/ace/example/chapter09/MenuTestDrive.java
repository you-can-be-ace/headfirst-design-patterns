package com.ace.example.chapter09;

import com.ace.example.chapter09.menu.DinerMenu;
import com.ace.example.chapter09.menu.PancakeHouseMenu;

public class MenuTestDrive {

  public static void main(String[] args) {
    var pancakeHouseMenu = new PancakeHouseMenu();
    var dinerMenu = new DinerMenu();

    var waitress = new Waitress(pancakeHouseMenu, dinerMenu);

    waitress.printMenu();
  }

}
