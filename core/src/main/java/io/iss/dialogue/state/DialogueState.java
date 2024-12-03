package io.iss.dialogue.state;

public interface DialogueState {
    void enter();
    void processInput();
    void update(float delta);
    void exit();
}
