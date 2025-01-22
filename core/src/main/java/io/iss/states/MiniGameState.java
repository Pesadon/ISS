package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import io.iss.minigames.MiniGame;
import io.iss.objects.InteractiveObject;
import io.iss.screens.GameScreen;

public class MiniGameState extends GameState implements MiniGame.MiniGameListener {
    private MiniGame miniGame;

    public MiniGameState(GameScreen screen) {
        super(screen);
    }

    @Override
    public void enter() {
        Texture objectTexture = new Texture("assets/images/notebook.png");

        InteractiveObject interactiveObject = new InteractiveObject(
            objectTexture,
            "notebook",
            this::startMiniGame,
            null
        );

        interactiveObject.setPosition(stage.getWidth() / 2 - objectTexture.getWidth() / 2,
            stage.getHeight() / 2 - objectTexture.getHeight() / 2);

        stage.addActor(interactiveObject);
        Gdx.input.setInputProcessor(stage);
    }

    private void startMiniGame() {
        miniGame = new MiniGame(stage, this);
        miniGame.start();
    }

    @Override
    public void update(float delta) {
        if (miniGame != null) {
            miniGame.update(delta);
        }
        super.update(delta);
    }

    @Override
    public void onMiniGameEnd(boolean success) {
        if (success) {
            System.out.println("MiniGame completed successfully!");
        } else {
            System.out.println("MiniGame failed.");
        }
    }

    @Override
    public void exit() {
        stage.clear();
        Gdx.input.setInputProcessor(null);
    }
}
