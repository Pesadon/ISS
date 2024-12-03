package io.iss.dialogue.model;

public class DialogueChoice {
    private String text;
    private String nextScene;

    public DialogueChoice() {}

    public DialogueChoice(String text, String nextScene) {
        this.text = text;
        this.nextScene = nextScene;
    }

    // Getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getNextScene() { return nextScene; }
    public void setNextScene(String nextScene) { this.nextScene = nextScene; }
}
