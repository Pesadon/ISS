package io.iss.factory;

import io.iss.screens.GameScreen;
import io.iss.states.GameState;
import io.iss.states.IntroState;
import io.iss.states.MenuState;
import io.iss.states.PlayState;

public class StateFactory {
    private final GameScreen screen;

    public StateFactory(GameScreen screen) {
        this.screen = screen;
    }

    public GameState createState(StateType type) {
        return switch (type) {
            case MENU -> new MenuState(this.screen);
            case INTRO -> new IntroState(this.screen);
            case PLAY -> new PlayState(this.screen);
            default -> throw new IllegalArgumentException("Unknown screen type: " + type);
        };
    }
}
