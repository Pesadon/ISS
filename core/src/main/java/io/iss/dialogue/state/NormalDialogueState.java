package io.iss.dialogue.state;

import com.badlogic.gdx.Gdx;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.model.DialogueEntry;

public class NormalDialogueState implements DialogueState {
    private DialogueContext context;
    private DialogueEntry currentDialogue;
    private float textDisplayTimer;
    private boolean isComplete;

    public NormalDialogueState(DialogueContext context, DialogueEntry dialogue) {
        this.context = context;
        this.currentDialogue = dialogue;
        this.textDisplayTimer = 0;
        this.isComplete = false;
    }

    @Override
    public void enter() {
        context.getDialogueUI().showCharacter(currentDialogue.getCharacter());
        context.getDialogueUI().startDisplayingText(currentDialogue.getText());
    }

    @Override
    public void processInput() {
        if (Gdx.input.justTouched()) {
            if (isComplete && context.hasNextDialogue()) {
                context.advance();
            } else {
                // Skip text animation if still displaying
                context.getDialogueUI().completeTextDisplay();
                isComplete = true;
            }
        }
    }

    @Override
    public void update(float delta) {
        textDisplayTimer += delta;
    }

    @Override
    public void exit() {
        context.getDialogueUI().hideCharacter();
    }
}

