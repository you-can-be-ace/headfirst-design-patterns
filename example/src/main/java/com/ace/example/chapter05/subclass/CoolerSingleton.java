package com.ace.example.chapter05.subclass;

public class CoolerSingleton extends Singleton {

	protected static Singleton uniqueInstance;
 
	private CoolerSingleton() {
		super();
	}
}
