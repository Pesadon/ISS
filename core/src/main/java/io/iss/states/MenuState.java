package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.iss.screens.GameScreen;
import io.iss.ui.tables.MainMenuTable;
import io.iss.utils.GameAssetManager;

public class MenuState extends GameState {
    // Background image for the menu
    private final Image backgroundImage;

    // The table that will contain our menu buttons
    private MainMenuTable menuTable;

    public MenuState(GameScreen screen) {
        super(screen);

        // Create an image actor from the texture
        backgroundImage = new Image(GameAssetManager.getInstance().get(GameAssetManager.MENU_BG_TEXTURE, Texture.class));

        // Make the background fill the entire stage
        backgroundImage.setFillParent(true);
    }

    @Override
    public void enter() {
        // Set up the stage as the input processor
        Gdx.input.setInputProcessor(stage);

        // Add the background as the first actor (bottom layer)
        stage.addActor(backgroundImage);

        // Create and add the menu table
        // You'll need to create a skin for your UI elements
        menuTable = new MainMenuTable(screen);

        // Center the menu table on the stage
        menuTable.setFillParent(true);

        // Add the menu table to the stage (top layer)
        stage.addActor(menuTable);

        // Set up input processing for the stage
        Gdx.input.setInputProcessor(stage);

        // Ensure the stage's viewport is centered
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        // Center the stage's camera
        stage.getCamera().position.set(
            stage.getCamera().viewportWidth / 2,
            stage.getCamera().viewportHeight / 2,
            0
        );
        stage.getCamera().update();
    }

    @Override
    public void exit() {
        stage.clear();
        menuTable.dispose();
    }

    @Override
    public void resize(int width, int height) {
        // This is crucial for maintaining proper centering when the window is resized
        stage.getViewport().update(width, height, true);

        // Re-center the camera after resize
        stage.getCamera().position.set(
            stage.getCamera().viewportWidth / 2,
            stage.getCamera().viewportHeight / 2,
            0
        );
        stage.getCamera().update();
    }
}
