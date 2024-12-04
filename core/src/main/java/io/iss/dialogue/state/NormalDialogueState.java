package io.iss.dialogue.state;

import com.badlogic.gdx.Gdx;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.model.DialogueEntry;

public class NormalDialogueState implements DialogueState {
    private final DialogueContext context;
    private final DialogueEntry currentDialogue;
    private float textDisplayTimer;
    private boolean isComplete;
    private boolean wasMousePressed;

    public NormalDialogueState(DialogueContext context, DialogueEntry dialogue) {
        this.context = context;
        this.currentDialogue = dialogue;
        this.textDisplayTimer = 0;
        this.isComplete = false;
        this.wasMousePressed = false;
    }

    @Override
    public void enter() {
        context.getDialogueUI().showCharacter(currentDialogue.getCharacter());
        context.getDialogueUI().startDisplayingText(currentDialogue.getText());
    }

    @Override
    public void processInput() {
        boolean isMousePressed = Gdx.input.isTouched();

        if (!isMousePressed && wasMousePressed) {
            if (context.hasNextDialogue()) {
                context.advance();
            } else {
                context.getDialogueUI().getDialogueBox().setVisible(false);
            }
        }

        wasMousePressed = isMousePressed;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void exit() {
        context.getDialogueUI().hideCharacter();
    }
}

