package io.iss.factory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import io.iss.screens.GameScreen;
import io.iss.states.*;

public class StateFactory {
    private static final String PREFERENCES_NAME = "StateFactoryPreferences";
    private static final String STATE_KEY = "SavedState";

    private final GameScreen screen;

    public StateFactory(GameScreen screen) {
        this.screen = screen;
    }

    public GameState createState(StateType type) {
        return switch (type) {
            case MENU -> new MenuState(screen);
            case TEST_ROOM -> new TestRoomState(screen);
            case TEST_ROOM2 -> new TestRoom2State(screen);
            case INTRO -> new IntroState(screen);
            case HELL_DIALOGUE -> new DialogueTest(screen);
        };
    }

    public StateType loadState() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        String savedState = prefs.getString(STATE_KEY, null);

        if (savedState != null) {
            try {
                return StateType.valueOf(savedState);
            } catch (IllegalArgumentException e) {
                System.err.println("Stato salvato non valido: " + savedState);
            }
        }

        return StateType.TEST_ROOM;
    }

    public void saveState(StateType type) {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        prefs.putString(STATE_KEY, type.name());
        prefs.flush();
    }
}
