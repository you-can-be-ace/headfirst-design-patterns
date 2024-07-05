package com.ace.example.chapter06.remote;

import com.ace.example.chapter06.command.Command;
import com.ace.example.chapter06.command.NoCommand;

public class RemoteWithUndo {
    Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;

    public RemoteWithUndo() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();

        for(int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }

        undoCommand = noCommand;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    public void undoButtonWasPushed() {
        undoCommand.undo();
    }

    @Override
    public String toString() {
        StringBuffer stringBuff = new StringBuffer();
        stringBuff.append("\n------ 리모컨 ------\n");

        // getClass().getName() : 패키지 + 클래스명 반환
//        for(int i = 0; i < onCommands.length; i++) {
//            stringBuff.append("[slot " + i + "] " + onCommands[i].getClass().getName()
//                    + "     " + offCommands[i].getClass().getName() + "\n");
//        }

        // getClass().getSimpleName() : 오직 클래스명만 반환
        for(int i = 0; i < onCommands.length; i++) {
            stringBuff.append("[slot " + i + "] " + onCommands[i].getClass().getSimpleName()
                    + "\t\t\t\t" + offCommands[i].getClass().getSimpleName() + "\n");
        }

        stringBuff.append("[undo] " + undoCommand.getClass().getSimpleName() + "\n");

        return stringBuff.toString();
    }
}
