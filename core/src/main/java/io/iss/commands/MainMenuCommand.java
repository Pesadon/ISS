package io.iss.commands;

import io.iss.factory.StateType;
import io.iss.screens.GameScreen;

public class MainMenuCommand implements MenuCommand {
    private final GameScreen screen;

    public MainMenuCommand(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void execute() {
        screen.setState(screen.getStateFactory().createState(StateType.MENU, false));
    }
}
