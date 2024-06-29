package com.ace.example.chapter05.subclass;

public class Singleton {

	protected static Singleton uniqueInstance;

	// 싱글톤 확장 시 생성자는 접근 제어자로 `protected` 선언 필요
	protected Singleton() {}
 
	public static synchronized Singleton getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Singleton();
		}
		return uniqueInstance;
	}
}
