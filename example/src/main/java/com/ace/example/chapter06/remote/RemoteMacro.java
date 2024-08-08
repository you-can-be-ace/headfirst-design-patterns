package com.ace.example.chapter06.remote;

import com.ace.example.chapter06.command.*;
import com.ace.example.chapter06.device.Hottub;
import com.ace.example.chapter06.device.Light;
import com.ace.example.chapter06.device.Stereo;
import com.ace.example.chapter06.device.TV;

public class RemoteMacro {
    public static void main(String[] args) {
        RemoteWithUndo remoteControl = new RemoteWithUndo();

        Light light = new Light("Living Room");
        TV tv = new TV("Living Room");
        Stereo stereo = new Stereo("Living Room");
        Hottub hottub = new Hottub();

        LightOnCommand lightOn = new LightOnCommand(light);
        StereoOnCommand stereoOn = new StereoOnCommand(stereo);
        TVOnCommand tvOn = new TVOnCommand(tv);
        HottubOnCommand hottubOn = new HottubOnCommand(hottub);

        LightOffCommand lightOff = new LightOffCommand(light);
        StereoOffCommand stereoOff = new StereoOffCommand(stereo);
        TVOffCommand tvOff = new TVOffCommand(tv);
        HottubOffCommand hottubOff = new HottubOffCommand(hottub);

        Command[] partyOn = { lightOn, stereoOn, tvOn, hottubOn };
        Command[] partyOff = { lightOff, stereoOff, tvOff, hottubOff };

        MacroCommand partyOnMacro = new MacroCommand(partyOn);
        MacroCommand partyOffMacro = new MacroCommand(partyOff);

        remoteControl.setCommand(0, partyOnMacro, partyOffMacro);

        System.out.println(remoteControl);
        System.out.println("--- 매크로 ON ---");
        remoteControl.onButtonWasPushed(0);

        System.out.println("\n--- 매크로 OFF ---");
        remoteControl.offButtonWasPushed(0);

        System.out.println("\n--- UNDO ---");
        remoteControl.undoButtonWasPushed();
    }
}
