package com.ace.example.chapter06.remote;

import com.ace.example.chapter06.command.*;
import com.ace.example.chapter06.device.CeilingFan;
import com.ace.example.chapter06.device.GarageDoor;
import com.ace.example.chapter06.device.Light;
import com.ace.example.chapter06.device.Stereo;

public class RemoteLoader {
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();
        RemoteWithUndo remoteWithUndo = new RemoteWithUndo();

        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        CeilingFan ceilingFan = new CeilingFan("Living Room");
        GarageDoor garageDoor = new GarageDoor("Garage");
        Stereo stereo = new Stereo("Living Room");

        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

        CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
        CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);

        GarageDoorUpCommand garageDoorUp = new GarageDoorUpCommand(garageDoor);
        GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(garageDoor);

        StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);

        remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
        remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff);
        remoteControl.setCommand(3, stereoOnWithCD, stereoOff);

        System.out.println(remoteControl);

        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonWasPushed(0);
        remoteControl.onButtonWasPushed(1);
        remoteControl.offButtonWasPushed(1);
        remoteControl.onButtonWasPushed(2);
        remoteControl.offButtonWasPushed(2);
        remoteControl.onButtonWasPushed(3);
        remoteControl.offButtonWasPushed(3);

        System.out.println("\n-------------\n");

        // Undo Button Test
        remoteWithUndo.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remoteWithUndo.setCommand(1, kitchenLightOn, kitchenLightOff);
        remoteWithUndo.setCommand(2, ceilingFanOn, ceilingFanOff);
        remoteWithUndo.setCommand(3, stereoOnWithCD, stereoOff);

        remoteWithUndo.onButtonWasPushed(0);
        remoteWithUndo.offButtonWasPushed(0);

        System.out.println(remoteWithUndo);
        remoteWithUndo.undoButtonWasPushed();

        remoteWithUndo.offButtonWasPushed(0);
        remoteWithUndo.onButtonWasPushed(0);

        System.out.println(remoteWithUndo);
        remoteWithUndo.undoButtonWasPushed();

        System.out.println("\n-------------\n");

        // CeilingFan Button Test
        RemoteWithUndo remoteWithUndo2 = new RemoteWithUndo();

        CelingFanMediumCommand ceilingFanMedium = new CelingFanMediumCommand(ceilingFan);
        CeilingFanHighCommand ceilingFanHigh = new CeilingFanHighCommand(ceilingFan);
        CeilingFanLowCommand ceilingFanLow = new CeilingFanLowCommand(ceilingFan);

        remoteWithUndo2.setCommand(0, ceilingFanMedium, ceilingFanOff);
        remoteWithUndo2.setCommand(1, ceilingFanHigh, ceilingFanOff);

        remoteWithUndo2.onButtonWasPushed(0);
        remoteWithUndo2.offButtonWasPushed(0);

        System.out.println(remoteWithUndo2);
        remoteWithUndo2.undoButtonWasPushed();

        remoteWithUndo2.onButtonWasPushed(1);

        System.out.println(remoteWithUndo2);
        remoteWithUndo2.undoButtonWasPushed();
    }
}
