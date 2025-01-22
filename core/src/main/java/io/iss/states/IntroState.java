package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.factory.StateType;
import io.iss.screens.GameScreen;
import io.iss.ui.PauseMenu;
import io.iss.utils.GameAssetManager;

public class IntroState extends GameState {
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;
    private final PauseMenu pauseMenu;

    public IntroState(GameScreen screen) {
        super(screen);

        dialogueLoader = new DialogueLoader(GameAssetManager.DIALOGUES_JSON);
        dialogueContext = new DialogueContext(screen, stage);
        pauseMenu = new PauseMenu(screen, stage, dialogueContext);
    }

    @Override
    public void enter() {
        Image backgroundImage = new Image(GameAssetManager.getInstance().get(GameAssetManager.NO_DEAD_DETECTIVE_BG, Texture.class));
        backgroundImage.setFillParent(true); // Make the image fill the stage

        //Character sister = new Character("Sister", 0, 0);
        //sister.addSprite(CharacterState.IDLE, new TextureRegion(GameAssetManager.getInstance().get(GameAssetManager.SISTER_IDLE_TEXTURE, Texture.class)));
        //sister.setPosition(Gdx.graphics.getWidth() / 2f - sister.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - sister.getHeight() / 2f);

        //Character detective = new Character("Detective", 0, 0);
        //detective.addSprite(CharacterState.IDLE, new TextureRegion(GameAssetManager.getInstance().get(GameAssetManager.DETECTIVE_IDLE_TEXTURE, Texture.class)));

        stage.addActor(backgroundImage);
        //stage.addActor(sister);
        //stage.addActor(detective);
        stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());

        //TODO: why do we need this first call of dialogue scene to make the actual dialogue work?
        dialogueContext.startScene(dialogueLoader.getScene("intro_death"));

        dialogueContext.startScene(dialogueLoader.getScene("intro_no_death"), () -> {
            stage.getActors().set(0, new Image(GameAssetManager.getInstance().get(GameAssetManager.DEAD_DETECTIVE_BG, Texture.class)));
            dialogueContext.startScene(dialogueLoader.getScene("intro_death"), () -> {
                screen.setState(screen.getStateFactory().createState(StateType.TEST_ROOM2, true));
            });
        });
        // Gdx.input.setInputProcessor(stage);

        initPauseButton();
        Gdx.input.setInputProcessor(stage);
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

    @Override
    public void update(float delta) {
        dialogueContext.update(delta);
        super.update(delta);
    }

    @Override
    public void exit() {
        Gdx.input.setInputProcessor(null);
    }
}
