package io.iss.dialogue.state;

import com.badlogic.gdx.utils.Array;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.model.DialogueChoice;

public class ChoiceDialogueState implements DialogueState {
    private DialogueContext context;
    private Array<DialogueChoice> choices;

    public ChoiceDialogueState(DialogueContext context, Array<DialogueChoice> choices) {
        this.context = context;
        this.choices = choices;
    }

    @Override
    public void enter() {
        context.getDialogueUI().showChoices(choices);
    }

    @Override
    public void processInput() {
        // Choice processing is handled by UI callbacks
    }

    @Override
    public void update(float delta) {}

    @Override
    public void exit() {
        context.getDialogueUI().hideChoices();
    }
}
