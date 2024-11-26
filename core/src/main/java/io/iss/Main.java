package io.iss;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;
    private int currentIndex = 0;
    private Array<String> textList;
    private Label textLabel;

    @Override
    public void create() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // list of dialogues - later from json
        textList = new Array<>();
        textList.add("First dialogue");
        textList.add("Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue");
        textList.add("Third dialogue");
        textList.add("Fourth dialogue");
        textList.add(Gdx.files.internal("commedy.txt").readString());

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

        textBox.setSize(stage.getWidth() / 1.2f, stage.getHeight() / 4f);
        textBox.setPosition(stage.getWidth() / 2f - textBox.getWidth() / 2f, 20);

        // Add the label and button to the table
        textBox.add(textLabel).expand().fill().pad(10f).row();
        textBox.add(button).pad(10f);

        // Add actors to the stage
        stage.addActor(textBox);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
