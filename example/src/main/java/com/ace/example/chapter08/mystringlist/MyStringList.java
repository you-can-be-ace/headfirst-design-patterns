package com.ace.example.chapter08.mystringlist;

import java.util.AbstractList;

public class MyStringList extends AbstractList<String> {
    private String[] myList;

    MyStringList(String[] strings) {
        myList = strings;
    }

    @Override
    public String get(int index) {
        return myList[index];
    }

    @Override
    public int size() {
        return myList.length;
    }

    public String set(int index, String item) {
        System.out.println("set 호출");
        String oldString = myList[index];
        myList[index] = item;
        return oldString;
    }
}
