package com.ace.example.chapter05.subclass;

public class SingletonTestDrive {

	public static void main(String[] args) {
		Singleton coolerSingleton = CoolerSingleton.getInstance();
		Singleton hotterSingleton = HotterSingleton.getInstance();

		// 출력 값 동일
		// 모두 부모 클래스인 `Singleton`의 싱글톤 객체 참조
		System.out.println(coolerSingleton);
		System.out.println(hotterSingleton);
 	}
}
