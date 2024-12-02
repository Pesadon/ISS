package io.iss.commands;

import io.iss.factory.StateType;
import io.iss.screens.GameScreen;

public class NewGameCommand implements MenuCommand {
    private final GameScreen screen;

    public NewGameCommand(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void execute() {
        screen.setState(screen.getStateFactory().createState(StateType.INTRO));
    }
}
