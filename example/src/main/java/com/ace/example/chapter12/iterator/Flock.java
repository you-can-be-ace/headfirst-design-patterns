package com.ace.example.chapter12.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Flock implements Quakable{

    List<Quakable> quackers = new ArrayList<Quakable>();

    public void add(Quakable quacker) {
        quackers.add(quacker);
    }

    @Override
    public void quack() {
        //반복자 패턴을 사용하여 Flock에 있는 Quakable 객체들을 순회하면서 quack() 메소드를 호출한다.
        Iterator<Quakable> iterator = quackers.iterator();

        while (iterator.hasNext()) {
            Quakable quakable = iterator.next();
            quakable.quack();
        }
    }
}