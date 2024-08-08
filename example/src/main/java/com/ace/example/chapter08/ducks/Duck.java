package com.ace.example.chapter08.ducks;

public class Duck implements Comparable<Duck>{
    String name;
    int weight;

    public Duck(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return name + " 체중: " + weight;
    }

    @Override
    public int compareTo(Duck object) { //무게가 더 적은 오리가 앞으로 가게끔 정렬
        Duck otherDuck = object;
        if (this.weight < otherDuck.weight) {
            return -1;
        } else if (this.weight == otherDuck.weight) {
            return 0;
        } else { // this.weight > otherDuck.weight
            return 1;
        }
    }
}
