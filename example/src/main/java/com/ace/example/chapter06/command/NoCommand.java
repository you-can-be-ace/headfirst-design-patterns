package com.ace.example.chapter06.command;

public class NoCommand implements Command {
    @Override
    public void execute() {}

    @Override
    public void undo() {}
}
