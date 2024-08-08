package com.ace.example.chapter05.threadsafe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadSafeSingleton1 {

    private static ThreadSafeSingleton1 uniqueInstance;

    public static synchronized ThreadSafeSingleton1 getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ThreadSafeSingleton1();
        }

        return uniqueInstance;
    }
}
