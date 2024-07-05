package com.ace.example.chapter06.command;

import com.ace.example.chapter06.device.CeilingFan;

public class CelingFanMediumCommand implements Command {
    CeilingFan ceilingFan;
    int prevSpeed;

    public CelingFanMediumCommand(CeilingFan ceilingFan) {
        this.ceilingFan = ceilingFan;
    }

    @Override
    public void execute() {
        prevSpeed = ceilingFan.getSpeed();
        ceilingFan.medium();
    }

    @Override
    public void undo() {
        if(prevSpeed == CeilingFan.HIGH) {
            ceilingFan.high();
        } else if(prevSpeed == CeilingFan.MEDIUM) {
            ceilingFan.medium();
        } else if(prevSpeed == CeilingFan.LOW) {
            ceilingFan.low();
        } else if(prevSpeed == CeilingFan.OFF) {
            ceilingFan.off();
        }
    }
}
