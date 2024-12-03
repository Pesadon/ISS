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

        dialogueLoader = new DialogueLoader("helldialog.json");
        dialogueContext = new DialogueContext(screen, stage);
    }

    @Override
    public void enter() {
        Texture backgroundTexture = new Texture("backgrounds/background.jpg");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true); // Make the image fill the stage

        Character player = new Character("Detective", 0, 0);
        player.addSprite(CharacterState.IDLE, new TextureRegion(GameAssetManager.getInstance().get(GameAssetManager.PLAYER_IDLE_TEXTURE, Texture.class)));
        player.setPosition(Gdx.graphics.getWidth() / 2f - player.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - player.getHeight() / 2f);

        stage.addActor(backgroundImage);
        stage.addActor(player);
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
