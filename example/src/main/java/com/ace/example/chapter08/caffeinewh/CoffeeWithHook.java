package com.ace.example.chapter08.caffeinewh;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CoffeeWithHook extends CaffeineBeverageWithHook {

    @Override
    void brew() {
        System.out.println("필터로 커피를 우려내는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("설탕과 우유를 추가하는 중");
    }

    @Override
    boolean customerWantsCondiments() { //후크를 오버라이드해서 원하는 기능을 넣는다.
        String answer = getUserInput();

        // 첨가물 추가 여부를 고객에게 물어보고 고객이 입력한 내용에 따라 boolean 값 리턴
        if (answer.toLowerCase().startsWith("y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 고객에게 우유와 설탕을 추가할지 말지를 물어보고, 명령줄로 추가 여부를 입력 받는다.
     * @return
     */
    private String getUserInput() {
        String answer = null;

        System.out.print("커피에 설탕과 우유를 넣어 드릴까요? (y/n)");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            answer = in.readLine();
        } catch (IOException e) {
            System.out.println("IO 오류");
        }

        if (answer == null) {
            return "no";
        }

        return answer;
    }
}
