package io.iss.states;

import com.badlogic.gdx.Gdx;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.screens.GameScreen;

public class DialogueTest extends GameState {
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;

    public DialogueTest(GameScreen screen) {
        super(screen);

        dialogueLoader = new DialogueLoader("helldialog.json");
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
