package io.iss.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import io.iss.controller.StageManager;

public class GameStage extends Stage {
    private int currentIndex = 0;
    private Array<String> textList;
    private Label textLabel;

    public GameStage(final StageManager stageManager, Skin skin) {
        // list of dialogues - later from json
        textList = new Array<>();
        textList.add("First dialogue");
        textList.add("Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue");
        textList.add("Third dialogue");
        textList.add("Fourth dialogue");
        //textList.add(Gdx.files.internal("comedy.txt").readString());

        // Create the label to display text
        textLabel = new Label(textList.get(currentIndex), skin);
        textLabel.setFontScale(2f);
        textLabel.setColor(Color.BLACK);
        textLabel.setWrap(true);
        textLabel.setAlignment(1);

        // Create the button to navigate through the text
        TextButton button = new TextButton("Next", skin);
        button.getLabel().setFontScale(2f);
        button.pad(10f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (currentIndex < textList.size - 1) {
                    currentIndex++;
                    textLabel.setText(textList.get(currentIndex));
                } else {
                    textLabel.setText("No more texts!");
                }
            }
        });

        // Create a table to act as a "text box"
        Table textBox = new Table();
        Drawable background = skin.newDrawable("white", Color.valueOf("#FFFACD")); // Pale yellow
        textBox.setBackground(background);

        textBox.setSize(getWidth() / 1.2f, getHeight() / 4f);
        textBox.setPosition(getWidth() / 2f - textBox.getWidth() / 2f, 20);

        // Add the label and button to the table
        textBox.add(textLabel).expand().fill().pad(10f).row();
        textBox.add(button).pad(10f);

        // Add actors to the stage
        addActor(textBox);

        Gdx.input.setInputProcessor(this);
    }
}
