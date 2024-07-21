package com.ace.example.chapter08.jframe;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public MyFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setVisible(true);
    }

    /**
     * JFrame 의 update() 알고리즘은 paint()를 호출한다.
     * paint 메소드를 오버라이드해서 원하는 기능을 넣는다.
     * JFrame 의 paint() 는 아무 일도 하지 않는다.
     * 그냥 후크 메소드이다.
     * @param graphics the specified Graphics window
     */
    public void paint(Graphics graphics) {
        super.paint(graphics);
        String msg = "내가 최고!!!!!!!!!!!!";
        graphics.drawString(msg, 100, 100);
    }

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame("Head First Design Patterns");
    }
}
