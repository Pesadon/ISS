package io.iss.dialogue.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.model.DialogueChoice;
import io.iss.screens.GameScreen;
import io.iss.utils.FontManager;

public class DialogueUI {
    private Stage stage;
    private Table dialogueTable;
    private Label textLabel;
    private Table choiceTable;
    private DialogueContext context;

    public DialogueUI(GameScreen screen, Stage stage) {
        this.stage = stage;
        setupUI(screen, stage);
    }

    private void setupUI(GameScreen screen, Stage stage) {
        textLabel = new Label("SIAMO ALLA FUFFA", new Label.LabelStyle(FontManager.getInstance().createRegularStyle()));
        textLabel.setWrap(true);

        choiceTable = new Table();
        dialogueTable = new Table();

        Drawable background = screen.getGame().getSkin().newDrawable("white", Color.valueOf("#FFFACD")); // Pale yellow

        dialogueTable.setBackground(background);
        dialogueTable.setSize(stage.getWidth() / 1.2f, stage.getHeight() / 4f);
        dialogueTable.setPosition(stage.getWidth() / 2f - dialogueTable.getWidth() / 2f, 20);

        dialogueTable.add(textLabel).width(600).pad(20);
        dialogueTable.add(choiceTable).row();

        stage.addActor(dialogueTable);
    }

    public void showCharacter(String character) {
        // Implementation for showing character sprite
    }

    public void hideCharacter() {
        // Implementation for hiding character sprite
    }

    public void startDisplayingText(String text) {
        textLabel.setText(text);
    }

    public void completeTextDisplay() {
        // Implementation for completing text animation
    }

    public void showChoices(Array<DialogueChoice> choices) {
        choiceTable.clear();

        for (final DialogueChoice choice : choices) {
            TextButton button = new TextButton(choice.getText(),
                new TextButton.TextButtonStyle());

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    handleChoice(choice);
                }
            });

            choiceTable.add(button).pad(10).row();
        }
    }

    public void hideChoices() {
        choiceTable.clear();
    }

    private void handleChoice(DialogueChoice choice) {
        // Implementation for handling choice selection
    }
}
