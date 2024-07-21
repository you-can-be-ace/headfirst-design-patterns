package com.ace.example.chapter08.caffeinewh;

public class CaffeineWithHookExample {
    public static void main(String[] args) {

        TeaWithHook teaWithHook = new TeaWithHook();
        CoffeeWithHook coffeeWithHook = new CoffeeWithHook();

        System.out.println("\n홍차 준비 중...");
        teaWithHook.prepareRecipe();

        System.out.println("\n커피 준비 중...");
        coffeeWithHook.prepareRecipe();
    }
}
