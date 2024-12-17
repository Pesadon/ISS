package io.iss.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.iss.dialogue.context.DialogueContext;
import io.iss.screens.GameScreen;

public class PauseMenu {
    private TextButton resumeButton;
    private TextButton quitButton;
    private Table pauseMenuTable;
    private DialogueContext dialogueContext;
    private Stage stage;
    private GameScreen screen;

    public PauseMenu(GameScreen screen, Stage stage, DialogueContext dialogueContext) {
        this.screen=screen;
        this.stage=stage;
        this.dialogueContext=dialogueContext;
    }

    public void showPauseMenu() {
        Skin skin = screen.getGame().getSkin();
        dialogueContext.pause();
        // Create a blocking layer to intercept input outside the pause menu
        Actor blockingLayer = new Actor();
        blockingLayer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        blockingLayer.setName("blockingLayer"); // Add a name for easier removal
        blockingLayer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Consume all touch events outside the pause menu
                return true;
            }
        });

        // Add the blocking layer to the stage
        stage.addActor(blockingLayer);

        // Create the pause menu table
        pauseMenuTable = new Table();
        pauseMenuTable.setFillParent(true);

        // Set background color
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.9f)); // Semi-transparent black
        pixmap.fill();
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pauseMenuTable.setBackground(backgroundDrawable);

        // Create buttons
        resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame(); // Resume the game when clicked
            }
        });

        quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Quit the game when clicked
            }
        });

        // Add buttons to the table
        pauseMenuTable.add(resumeButton).fillX().pad(10).center();
        pauseMenuTable.row().pad(10);
        pauseMenuTable.add(quitButton).fillX().pad(10).center();

        // Add the pause menu to the stage
        stage.addActor(pauseMenuTable);

        // Disable game input while pause menu is active
        Gdx.input.setInputProcessor(stage);
    }

    private void resumeGame() {
        // Remove the pause menu table
        if (pauseMenuTable != null) {
            pauseMenuTable.remove();
            pauseMenuTable = null;
            dialogueContext.resume();
        }

        // Remove the blocking layer
        Actor blockingLayer = stage.getRoot().findActor("blockingLayer");
        if (blockingLayer != null) {
            blockingLayer.remove();
        }

        // Re-enable game input
        Gdx.input.setInputProcessor(stage);
    }
}
