package io.iss.screens;

import com.badlogic.gdx.Screen;
import io.iss.MainGame;
import io.iss.factory.StateFactory;
import io.iss.factory.StateType;
import io.iss.states.GameState;

public class GameScreen implements Screen {
    private final MainGame game;
    private GameState currentState;
    private final StateFactory stateFactory;

    public GameScreen(MainGame game) {
        this.game = game;
        this.stateFactory = new StateFactory(this);

        setState(stateFactory.createState(StateType.MENU, false));
    }

    public void setState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
        }

        // Enter the new state
        currentState = newState;
        currentState.enter();
    }

    public StateFactory getStateFactory() {
        return stateFactory;
    }

    public MainGame getGame() {
        return game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (currentState != null) {
            currentState.render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        if (currentState != null) {
            currentState.resize(width, height);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
