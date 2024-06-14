package com.ace.example.chapter03.decorator;

import com.ace.example.chapter03.component.Beverage;

public abstract class CondimentDecorator extends Beverage {
    Beverage beverage;

    public abstract String getDescription();
}
