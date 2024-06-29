package com.ace.example.chapter05.threadsafe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadSafeSingleton2 {

    private static ThreadSafeSingleton2 uniqueInstance = new ThreadSafeSingleton2();

    public static synchronized ThreadSafeSingleton2 getInstance() {
        return uniqueInstance;
    }
}
