package com.ace.example.chapter06.command;

import com.ace.example.chapter06.device.Hottub;

public class HottubOnCommand implements Command {
    Hottub hottub;

    public HottubOnCommand(Hottub hottub) {
        this.hottub = hottub;
    }

    @Override
    public void execute() {
        hottub.on();
        hottub.setTemperature(40);
    }

    @Override
    public void undo() {
        hottub.off();
    }
}
