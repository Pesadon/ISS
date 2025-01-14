package io.iss.factory;

import io.iss.screens.GameScreen;
import io.iss.states.*;

public class StateFactory {
    private final GameScreen screen;

    public StateFactory(GameScreen screen) {
        this.screen = screen;
    }

    public GameState createState(StateType type) {
        return switch (type) {
            case MENU -> new MenuState(screen);
            case TEST_ROOM -> new TestRoomState(screen);
            case INTRO -> new IntroState(screen);
            case HELL_DIALOGUE -> new DialogueTest(screen);
        };
    }
}
