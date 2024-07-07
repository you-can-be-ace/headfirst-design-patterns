package com.ace.example.chapter05.threadsafe;

import com.ace.example.chapter05.basic.Singleton;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadSafeSingleton3 {

    private volatile static ThreadSafeSingleton3 uniqueInstance;

    public static ThreadSafeSingleton3 getInstance() {
        if (uniqueInstance == null) { // 인스턴스 존재 여부 확인
            synchronized (Singleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new ThreadSafeSingleton3();
                }
            }
        }

        return uniqueInstance;
    }
}
