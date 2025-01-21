package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.objects.GameObject;
import io.iss.objects.InteractiveObject;
import io.iss.screens.GameScreen;
import io.iss.ui.Inventory;
import io.iss.ui.JournalManager;
import io.iss.ui.JournalWindow;
import io.iss.ui.PauseMenu;
import io.iss.utils.GameAssetManager;

public class TestRoom2State extends GameState {

    private final PauseMenu pauseMenu;
    private final JournalWindow journalWindow;
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;

    public TestRoom2State(GameScreen screen) {
        super(screen);

        JournalManager.getInstance().prependTextWithId("TestRoom2Enter", "Another room...");

        dialogueLoader = new DialogueLoader(GameAssetManager.DIALOGUES_JSON);
        dialogueContext = new DialogueContext(screen, stage);

        pauseMenu = new PauseMenu(screen, stage, dialogueContext);
        initPauseButton();

        journalWindow = new JournalWindow(screen, stage, dialogueContext);
        initJournalButton();
    }

    @Override
    public void enter() {
        Table table = new Table();
        table.setFillParent(true);

        //TODO: why do we need this first call of dialogue scene to make the actual dialogue work?
        dialogueContext.startScene(dialogueLoader.getScene("its_dante"));

        GameObject littleDante = new GameObject("dante", new Texture("images/Dante.jpg"), () -> {
            Inventory.getInstance().hide();
            stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());
            dialogueContext.startScene(dialogueLoader.getScene("its_dante"), () -> {
                dialogueContext.getDialogueUI().getDialogueBox().remove();
                JournalManager.getInstance().prependTextWithId("TestRoom2Dante", "I've found Dante, but I'm not sure of what to do with it");
                Inventory.getInstance().show();
            });
        });

        if (!Inventory.getInstance().isCollected(littleDante.getId())) {
            littleDante.setPosition(600, 100);
            littleDante.setSize(100, 100);
            stage.addActor(littleDante);
        }

        stage.addActor(Inventory.getInstance().getInventoryBar());

        //TODO: this kind of code should be put in a superclass for stages in which the inventory is used
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor target = event.getTarget();

                if (!(target instanceof InteractiveObject) && !(target instanceof Table)) {
                    Inventory.getInstance().deselectItem();
                    Inventory.getInstance().removeGuiSelection();
                }
                return true;
            }
        });

        // Set up input processing
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void update(float delta) {
        dialogueContext.update(delta);
        super.update(delta);
    }

    @Override
    public void exit() {
        stage.clear();
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }

    public void initPauseButton() {
        // Load the image for the button (you can replace "pauseImage" with your image name)
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("assets/ui/pausebutton.png")));
        buttonStyle.down = buttonStyle.up; // Optionally, add a "pressed" state image if needed

        // Create the image button with the style
        ImageButton pauseButton = new ImageButton(buttonStyle);

        // Make the button square (adjust the size to your liking)
        float buttonSize = 80f; // Set the size of the button
        pauseButton.setSize(buttonSize, buttonSize);

        // Position the button in the top-left corner
        float buttonPadding = 20f; // Padding from the edges
        pauseButton.setPosition(buttonPadding, Gdx.graphics.getHeight() - pauseButton.getHeight() - buttonPadding);

        // Add the listener to handle the button click
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.showPauseMenu(); // Show the pause menu when the button is clicked
            }
        });

        // Add the button to the stage
        stage.addActor(pauseButton);
    }

    public void initJournalButton() {
        // Load the image for the button (you can replace "pauseImage" with your image name)
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("assets/images/notebook.png")));
        buttonStyle.down = buttonStyle.up; // Optionally, add a "pressed" state image if needed

        // Create the image button with the style
        ImageButton journalButton = new ImageButton(buttonStyle);

        // Make the button square (adjust the size to your liking)
        float buttonSize = 80f; // Set the size of the button
        journalButton.setSize(buttonSize, buttonSize);

        // Position the button in the top-left corner
        float buttonPadding = 20f; // Padding from the edges
        journalButton.setPosition(Gdx.graphics.getWidth() - buttonSize - buttonPadding, Gdx.graphics.getHeight() - journalButton.getHeight() - buttonPadding);

        // Add the listener to handle the button click
        journalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                journalWindow.showJournalWindow(); // Show the pause menu when the button is clicked
            }
        });

        // Add the button to the stage
        stage.addActor(journalButton);
    }
}
