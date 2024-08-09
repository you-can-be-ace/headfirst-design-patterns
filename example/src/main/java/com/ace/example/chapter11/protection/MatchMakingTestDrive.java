package com.ace.example.chapter11.protection;

import java.lang.reflect.*;
import java.util.*;

public class MatchMakingTestDrive {
    HashMap<String, Person> datingDB = new HashMap<String, Person>();

    public static void main(String[] args) {
        MatchMakingTestDrive test = new MatchMakingTestDrive();
        test.drive();
    }

    public MatchMakingTestDrive() {
        initializeDatabase();
    }

    public void drive() {
        Person kim = getPersonFromDatabase("김자바");
        Person ownerProxy = getOwnerProxy(kim);
        System.out.println("이름: " + ownerProxy.getName());
        ownerProxy.setInterests("볼링, 바둑");
        System.out.println("본인 프록시에 관심 사항을 등록합니다.");
        try {
            ownerProxy.setGeekRating(10);
        } catch (Exception e) {
            System.out.println("본인 프록시에는 괴짜 지수를 매길 수 없습니다.");
        }
        System.out.println("괴짜 지수: " + ownerProxy.getGeekRating());

        Person nonOwnerProxy = getNonOwnerProxy(kim);
        System.out.println("이름: " + nonOwnerProxy.getName());
        try {
            nonOwnerProxy.setInterests("볼링, 바둑");
        } catch (Exception e) {
            System.out.println("타인 프록시에는 관심 사항을 등록할 수 없습니다.");
        }
        nonOwnerProxy.setGeekRating(3);
        System.out.println("타인 프록시에 괴짜 지수를 매깁니다.");
        System.out.println("괴짜 지수: " + nonOwnerProxy.getGeekRating());
    }

    Person getOwnerProxy(Person person) {

        return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person));
    }

    Person getNonOwnerProxy(Person person) {

        return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(person));
    }

    Person getPersonFromDatabase(String name) {
        return (Person)datingDB.get(name);
    }

    void initializeDatabase() {
        Person kim = new PersonImpl();
        kim.setName("김자바");
        kim.setInterests("자동차, 컴퓨터, 음악");
        kim.setGeekRating(7);
        datingDB.put(kim.getName(), kim);

        Person hong = new PersonImpl();
        hong.setName("홍길동");
        hong.setInterests("수집, 영화, 음악");
        hong.setGeekRating(6);
        datingDB.put(hong.getName(), hong);
    }
}
