package io.iss.commands;

import io.iss.factory.StateType;
import io.iss.screens.GameScreen;
import io.iss.ui.Inventory;

public class NewGameCommand implements MenuCommand {
    private final GameScreen screen;

    public NewGameCommand(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void execute() {
        Inventory.getInstance().init();
        screen.setState(screen.getStateFactory().createState(StateType.INTRO));
    }
}
