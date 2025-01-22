package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.iss.minigames.MiniGame;
import io.iss.screens.GameScreen;
import io.iss.utils.GameAssetManager;

public class MiniGameState extends GameState implements MiniGame.MiniGameListener {
    private MiniGame miniGame;

    public MiniGameState(GameScreen screen) {
        super(screen);
    }

    @Override
    public void enter() {
        // Add background
        Image backgroundImage = new Image(GameAssetManager.getInstance().get(GameAssetManager.OFFICE_TEXTURE, Texture.class));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Create a custom transparent panel
        Actor transparentPanel = new Actor();
        transparentPanel.setSize(300, 300); // Size of the panel
        transparentPanel.setPosition(
            (stage.getWidth() / 2 - 100) + 100, // Center alignment
            stage.getHeight() / 2 - 100
        );

        // Add click event to the transparent panel
        transparentPanel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startMiniGame();
            }
        });

        // Add the transparent panel to the Stage
        stage.addActor(transparentPanel);

        // Set the input processor
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
