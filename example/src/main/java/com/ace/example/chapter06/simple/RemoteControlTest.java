package com.ace.example.chapter06.simple;

import com.ace.example.chapter06.command.GarageDoorUpCommand;
import com.ace.example.chapter06.command.LightOnCommand;
import com.ace.example.chapter06.device.GarageDoor;
import com.ace.example.chapter06.device.Light;

public class RemoteControlTest {
    public static void main(String[] args) {
        SimpleRemoteControl remote = new SimpleRemoteControl();
        Light light = new Light("Living Room");
        GarageDoor garageDoor = new GarageDoor("Garage");
        LightOnCommand lightOn = new LightOnCommand(light);
        GarageDoorUpCommand garageOpen = new GarageDoorUpCommand(garageDoor);

        remote.setCommand(lightOn);
        remote.buttonWasPressed();
        remote.setCommand(garageOpen);
        remote.buttonWasPressed();
    }
}
