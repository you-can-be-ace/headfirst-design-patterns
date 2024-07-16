package com.ace.example.chapter08.mystringlist;

import java.util.List;

public class MyStringListTestDrive {

    public static void main(String args[]) {

        String[] ducks = { "Mallard Duck", "Redhead Duck", "Rubber Duck", "Decoy Duck" };
        MyStringList duckList = new MyStringList(ducks);
        List ducksSubList = duckList.subList(2, 4); //"Rubber Duck", "Decoy Duck"

        System.out.println("원본 리스트:");
        for (String duck : ducks) {
            System.out.println(duck);
        }

        //원본리스트 0번째 방 Duck : Mallard Duck
        System.out.println("원본리스트 0번째 방 Duck : " + duckList.get(0));

        System.out.println("\n서브 리스트:");
        //서브리스트 0번째 방 Duck : Rubber Duck
        System.out.println("서브리스트 0번째 방 Duck : " + ducksSubList.get(0));
        ducksSubList.set(0, "Rubber Duck Modified");
        System.out.println("서브리스트 0번째 방 Duck : " + ducksSubList.get(0));
        for (Object duck : ducksSubList) {
            System.out.println(duck);
        }

    }
}
