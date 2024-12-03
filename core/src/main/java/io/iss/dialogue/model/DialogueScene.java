package io.iss.dialogue.model;

import com.badlogic.gdx.utils.Array;

public class DialogueScene {
    private String mapPath;
    private Array<DialogueEntry> dialogues;

    public DialogueScene(String mapPath, Array<DialogueEntry> dialogues) {
        this.mapPath = mapPath;
        this.dialogues = dialogues;
    }

    public Array<DialogueEntry> getDialogues() {
        return dialogues;
    }

    public DialogueEntry getDialogueAt(int index) {
        return index < dialogues.size ? dialogues.get(index) : null;
    }

    public int getDialogueCount() {
        return dialogues.size;
    }

    public String getMapPath() { return mapPath; }
}
