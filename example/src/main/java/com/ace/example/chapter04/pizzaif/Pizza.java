package com.ace.example.chapter04.pizzaif;

import java.util.ArrayList;
import java.util.List;

/**
 * 팩토리에서 생산한 원재료만 사용하도록
 */
public abstract class Pizza {
    String name;
    Dough dough;
    Sauce sauce;
    Veggies veggies[];
    Cheese cheese;
    Pepperoni pepperoni;
    Clams clams;

    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}

    public void getName(String name) {
        this.name = name;
    }

    abstract void prepare();

    void bake() {
        System.out.println("175도에서 25분 간 굽기");
    }

    void cut() {
        System.out.println("피자를 사선으로 자르기");
    }

    void box() {
        System.out.println("상자에 피자 담기");
    }

    public String toString() {
        // code to display pizza name and ingredients
        StringBuffer display = new StringBuffer();
        display.append("---- " + name + " ----\n");
        display.append(dough + "\n");
        display.append(sauce + "\n");
        for (Veggies veggie : veggies) {
            display.append(veggie.toString() + "\n");
        }
        return display.toString();
    }

}
