package com.ace.example.chapter05.enumeration;

public enum Singleton {

    UNIQUE_INSTANCE;

    public String getDescription() {
        return "Thread-Safe Singleton";
    }
}
