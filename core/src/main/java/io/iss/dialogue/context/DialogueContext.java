package io.iss.dialogue.context;

import io.iss.dialogue.model.DialogueEntry;
import io.iss.dialogue.model.DialogueScene;
import io.iss.dialogue.ui.DialogueUI;
import io.iss.dialogue.state.ChoiceDialogueState;
import io.iss.dialogue.state.DialogueState;
import io.iss.dialogue.state.NormalDialogueState;

public class DialogueContext {
    private DialogueState currentState;
    private DialogueScene currentScene;
    private DialogueUI dialogueUI;
    private int currentDialogueIndex;

    public DialogueContext(DialogueUI dialogueUI) {
        this.dialogueUI = dialogueUI;
    }

    public void startScene(DialogueScene scene) {
        this.currentScene = scene;
        currentDialogueIndex = 0;
        setInitialState();
    }

    private void setInitialState() {
        // Get the first dialogue entry
        DialogueEntry firstDialogue = currentScene.getDialogueAt(0);

        // Choose appropriate state based on dialogue type
        if (firstDialogue.hasChoices()) {
            setState(new ChoiceDialogueState(this, firstDialogue.getChoices()));
        } else {
            setState(new NormalDialogueState(this, firstDialogue));
        }
    }

    public void advance() {
        if (hasNextDialogue()) {
            currentDialogueIndex++;
            DialogueEntry nextDialogue = currentScene.getDialogueAt(currentDialogueIndex);

            if (nextDialogue.hasChoices()) {
                setState(new ChoiceDialogueState(this, nextDialogue.getChoices()));
            } else {
                setState(new NormalDialogueState(this, nextDialogue));
            }
        }
    }

    public void setState(DialogueState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        currentState = newState;
        currentState.enter();
    }

    public boolean hasNextDialogue() {
        return currentDialogueIndex < currentScene.getDialogueCount() - 1;
    }

    public void update(float delta) {
        if (currentState != null) {
            currentState.processInput();
            currentState.update(delta);
        }
    }

    public DialogueUI getDialogueUI() {
        return dialogueUI;
    }
}
