package io.iss.states;

import com.badlogic.gdx.Gdx;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.screens.GameScreen;
import io.iss.utils.GameAssetManager;

public class OfficeState extends GameState {
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;

    public OfficeState(GameScreen screen) {
        super(screen);

        dialogueLoader = new DialogueLoader(GameAssetManager.DIALOGUES_JSON);
        dialogueContext = new DialogueContext(screen, stage);
    }

    @Override
    public void enter() {
        dialogueContext.startScene(dialogueLoader.getScene("office_scene"));
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
