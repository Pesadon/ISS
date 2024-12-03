package io.iss.dialogue.model;

import com.badlogic.gdx.utils.Array;

import java.util.List;

public class DialogueEntry {
    private String character;
    private String text;
    private Array<DialogueChoice> choices;

    public DialogueEntry() {
        choices = new Array<>();
    }

    public boolean hasChoices() {
        return choices.size > 0;
    }

    // Getters and setters
    public String getCharacter() { return character; }
    public void setCharacter(String character) { this.character = character; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Array<DialogueChoice> getChoices() { return choices; }
    public void setChoices(Array<DialogueChoice> choices) { this.choices = choices; }
}
