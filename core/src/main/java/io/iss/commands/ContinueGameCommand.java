package io.iss.commands;

import io.iss.screens.GameScreen;
import io.iss.ui.JournalManager;

public class ContinueGameCommand implements MenuCommand {
    private final GameScreen screen;

    public ContinueGameCommand(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void execute() {
        //Inventory.load();
        JournalManager.load();
        screen.setState(screen.getStateFactory().createState(screen.getStateFactory().loadState(), false));
    }
}
