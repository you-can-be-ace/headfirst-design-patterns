package com.ace.example.chapter09;

import com.ace.example.chapter09.menu.CafeMenu;
import com.ace.example.chapter09.menu.DinerMenu;
import com.ace.example.chapter09.menu.PancakeHouseMenu;
import java.util.List;

public class MenuTestDrive {

  public static void main(String[] args) {
    var pancakeHouseMenu = new PancakeHouseMenu();
    var dinerMenu = new DinerMenu();
    var cafeMenu = new CafeMenu();

    var menus = List.of(pancakeHouseMenu, dinerMenu, cafeMenu);
    var waitress = new Waitress(menus);

    waitress.printMenu();
  }

}
