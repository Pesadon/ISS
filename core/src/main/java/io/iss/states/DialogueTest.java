package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import io.iss.characters.CharacterState;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.dialogue.model.DialogueScene;
import io.iss.dialogue.ui.DialogueUI;
import io.iss.screens.GameScreen;
import io.iss.utils.FontManager;
import io.iss.characters.Character;
import io.iss.utils.GameAssetManager;

public class DialogueTest extends GameState {
    private DialogueContext dialogueContext;
    private DialogueLoader dialogueLoader;

    public DialogueTest(GameScreen screen) {
        super(screen);
    }

    @Override
    public void enter() {
        DialogueUI dialogueUI = new DialogueUI(screen, stage);
        dialogueContext = new DialogueContext(dialogueUI);
        dialogueLoader = new DialogueLoader();

        // Load dialogues
        dialogueLoader.loadDialogues("helldialog.json");

        // Start with initial scene
        DialogueScene initialScene = dialogueLoader.getScene("office_intro");
        dialogueContext.startScene(initialScene);
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
