package com.ace.example.chapter12.decorate;

public class DuckCall implements Quakable {
    @Override
    public void quack() {
        System.out.println("꽉꽉");
    }
}
