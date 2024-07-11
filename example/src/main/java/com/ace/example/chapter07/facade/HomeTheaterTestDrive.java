package com.ace.example.chapter07.facade;

import com.ace.example.chapter07.facade.system.Amplifier;
import com.ace.example.chapter07.facade.system.PopcornPopper;
import com.ace.example.chapter07.facade.system.Projector;
import com.ace.example.chapter07.facade.system.Screen;
import com.ace.example.chapter07.facade.system.StreamingPlayer;
import com.ace.example.chapter07.facade.system.TheaterLights;
import com.ace.example.chapter07.facade.system.Tuner;

public class HomeTheaterTestDrive {

  public static void main(String[] args) {

    Amplifier amp = new Amplifier();
    Tuner tuner = new Tuner();
    StreamingPlayer player = new StreamingPlayer();
    Projector projector = new Projector();
    Screen screen = new Screen();
    TheaterLights lights = new TheaterLights();
    PopcornPopper popper = new PopcornPopper();

    HomeTheaterFacade homeTheater = new HomeTheaterFacade(
        amp, tuner, player, projector, lights, screen, popper);

    homeTheater.watchMovie("인디아나 존스:레이더스");
    homeTheater.endMovie();
  }

}
