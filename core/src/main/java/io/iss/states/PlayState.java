package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.iss.characters.CharacterState;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.screens.GameScreen;
import io.iss.characters.Character;
import io.iss.ui.PauseMenu;
import io.iss.utils.GameAssetManager;

public class PlayState extends GameState {
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;
    private PauseMenu pauseMenu;

    public PlayState(GameScreen screen) {
        super(screen);

        dialogueLoader = new DialogueLoader(GameAssetManager.DIALOGUES_JSON);
        dialogueContext = new DialogueContext(screen, stage);
        pauseMenu = new PauseMenu(screen, stage, dialogueContext);
    }

    @Override
    public void enter() {
        Image backgroundImage = new Image(GameAssetManager.getInstance().get(GameAssetManager.OFFICE_TEXTURE, Texture.class));
        backgroundImage.setFillParent(true); // Make the image fill the stage

        Character sister = new Character("Sister", 0, 0);
        sister.addSprite(CharacterState.IDLE, new TextureRegion(GameAssetManager.getInstance().get(GameAssetManager.SISTER_IDLE_TEXTURE, Texture.class)));
        sister.setPosition(Gdx.graphics.getWidth() / 2f - sister.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - sister.getHeight() / 2f);

        Character detective = new Character("Detective", 0, 0);
        detective.addSprite(CharacterState.IDLE, new TextureRegion(GameAssetManager.getInstance().get(GameAssetManager.DETECTIVE_IDLE_TEXTURE, Texture.class)));

        stage.addActor(backgroundImage);
        stage.addActor(sister);
        stage.addActor(detective);
        stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());

        dialogueContext.startScene(dialogueLoader.getScene("intro_death"));

        initPauseButton();

        Gdx.input.setInputProcessor(stage);
    }

    public void initPauseButton() {
        Skin skin = screen.getGame().getSkin();
        TextButton pauseButton = new TextButton("Pause", skin);
        pauseButton.setPosition(50, 50); // Position the button (you can adjust this as needed)
        pauseButton.addListener(new ClickListener() { // Use ClickListener for the button
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.showPauseMenu(); // Show the pause menu when the button is clicked
            }
        });
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
