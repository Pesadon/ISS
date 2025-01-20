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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.iss.dialogue.context.DialogueContext;
import io.iss.screens.GameScreen;
import io.iss.utils.FontManager;

public class JournalWindow {
    private Table journalTable;
    private DialogueContext dialogueContext;
    private Stage stage;
    private GameScreen screen;

    public JournalWindow(GameScreen screen, Stage stage, DialogueContext dialogueContext) {
        this.screen = screen;
        this.stage = stage;
        this.dialogueContext = dialogueContext;
    }

    public void showJournalWindow() {
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
        journalTable = new Table();
        journalTable.setZIndex(1);
        journalTable.setFillParent(true);

        // Set background color
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.7f)); // Semi-transparent black
        pixmap.fill();
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        journalTable.setBackground(backgroundDrawable);

        Label titleLabel = new Label("Journal", FontManager.getInstance().createTitleStyle());
        titleLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(0.3f)
        ));
        journalTable.add(titleLabel).padTop(10).row();

        Label label = new Label(JournalManager.getInstance().getContent(), FontManager.getInstance().createTitleStyle());
        label.setWrap(true);

        ScrollPane scrollPane = new ScrollPane(label, skin);
        scrollPane.setScrollingDisabled(true, false);

        journalTable.add(scrollPane).expand().fill().pad(80).row();

        stage.addListener(new ScrollInputListener(scrollPane));

        // Add the pause menu to the stage
        stage.addActor(journalTable);

        showBackButton();

        // Disable game input while pause menu is active
        Gdx.input.setInputProcessor(stage);
    }

    private void resumeGame() {
        // Remove the pause menu table
        if (journalTable != null) {
            journalTable.remove();
            journalTable = null;
            dialogueContext.resume();
        }

        // Remove the blocking layer
        Actor blockingLayer = stage.getRoot().findActor("blockingLayer");
        if (blockingLayer != null) {
            blockingLayer.remove();
        }

        Actor backButton = stage.getRoot().findActor("backButton");
        if (backButton != null) {
            backButton.remove();
        }

        // Re-enable game input
        Gdx.input.setInputProcessor(stage);
    }

    private void showBackButton(){
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("assets/images/notebook.png")));
        buttonStyle.down = buttonStyle.up;

        ImageButton backButton = new ImageButton(buttonStyle);
        backButton.setName("backButton");

        float buttonSize = 80f;
        backButton.setSize(buttonSize, buttonSize);

        float buttonPadding = 20f;
        backButton.setPosition(Gdx.graphics.getWidth() - buttonSize - buttonPadding, Gdx.graphics.getHeight() - backButton.getHeight() - buttonPadding);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame();
            }
        });

        backButton.setZIndex(2);

        stage.addActor(backButton);
    }


    private static class ScrollInputListener extends com.badlogic.gdx.scenes.scene2d.InputListener {
        private final ScrollPane scrollPane;

        public ScrollInputListener(ScrollPane scrollPane) {
            this.scrollPane = scrollPane;
        }

        @Override
        public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
            scrollPane.setScrollY(scrollPane.getScrollY() + amountY * 20); // Regola la velocità di scroll
            return true;
        }
    }
}

