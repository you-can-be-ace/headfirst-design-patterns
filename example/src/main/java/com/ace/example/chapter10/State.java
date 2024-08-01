package com.ace.example.chapter10;

public interface State {
 
	void insertQuarter();
	void ejectQuarter();
	void turnCrank();
	void dispense();

	void refill();
}
