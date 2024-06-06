package com.ace.example.chapter02.swing;

import javax.swing.*;
import java.awt.*;
	
public class SwingObserverExample {

	JFrame frame;
	
	public static void main(String[] args) {
		SwingObserverExample example = new SwingObserverExample();
		example.go();
	}
	
	public void go() {
		frame = new JFrame();

		JButton button = new JButton("할까? 말까?");

		button.addActionListener(event -> 
			System.out.println("하지마! 아마 후회할 걸?")
		);
		button.addActionListener(event ->
			System.out.println("그냥 저질러 버렷!!!")
		);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.CENTER, button);
		frame.setSize(300,300);
		frame.setVisible(true);
	}
}
