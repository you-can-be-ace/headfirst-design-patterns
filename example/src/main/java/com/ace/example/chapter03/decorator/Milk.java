package com.ace.example.chapter03.decorator;

import com.ace.example.chapter03.component.Beverage;

public class Milk extends CondimentDecorator {
    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 우유";
    }

    @Override
    public double cost() {
        return beverage.cost() + .10;
    }
}
