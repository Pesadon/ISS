package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.iss.characters.CharacterState;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.screens.GameScreen;
import io.iss.characters.Character;
import io.iss.utils.GameAssetManager;

public class PlayState extends GameState {
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;

    public PlayState(GameScreen screen) {
        super(screen);

        dialogueLoader = new DialogueLoader(GameAssetManager.DIALOGUES_JSON);
        dialogueContext = new DialogueContext(screen, stage);
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
        // Gdx.input.setInputProcessor(stage);
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
