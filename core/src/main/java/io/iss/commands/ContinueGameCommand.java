package io.iss.commands;

import io.iss.screens.GameScreen;

import java.awt.*;

public class ContinueGameCommand implements MenuCommand {
    private final GameScreen screen;

    public ContinueGameCommand(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void execute() { }
}
