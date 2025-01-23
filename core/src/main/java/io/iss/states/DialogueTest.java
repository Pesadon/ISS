package io.iss.states;

import com.badlogic.gdx.Gdx;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.factory.StateType;
import io.iss.screens.GameScreen;
import io.iss.utils.GameAssetManager;

public class DialogueTest extends GameState {
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;

    public DialogueTest(GameScreen screen) {
        super(screen);

        this.type = StateType.HELL_DIALOGUE;

        dialogueLoader = new DialogueLoader(GameAssetManager.DIALOGUES_JSON);
        dialogueContext = new DialogueContext(screen, stage);
    }

    @Override
    public void enter() {
        dialogueContext.startScene(dialogueLoader.getScene("intro_death"));
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
