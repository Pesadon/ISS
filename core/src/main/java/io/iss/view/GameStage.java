package io.iss.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.iss.controller.StageManager;

public class GameStage extends Stage {
    private int currentIndex = 0;
    private final Array<String> textList;
    private final Label textLabel;

    public GameStage(final StageManager stageManager, Skin skin) {
        super(new ScreenViewport());

        Texture backgroundTexture = new Texture("backgrounds/background.jpg");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true); // Make the image fill the stage
        addActor(backgroundImage);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

        // list of dialogues - later from json
        textList = new Array<>();
        textList.add("First dialogue");
        textList.add("Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue");
        textList.add("Third dialogue");
        textList.add("Fourth dialogue");
        textList.add(Gdx.files.internal("comedy.txt").readString());

        var textLabelStyle = new Label.LabelStyle();
        textLabelStyle.font = font12;

        // Create the label to display text
        textLabel = new Label(textList.get(currentIndex), skin);
        textLabelStyle.font = font12;
        textLabel.setStyle(textLabelStyle);
        // textLabel.setFontScale(2f);
        textLabel.setColor(Color.BLACK);
        textLabel.setWrap(true);
        textLabel.setAlignment(1);

        // Create the button to navigate through the text
        TextButton button = new TextButton("Next", skin);
        // button.getLabel().setFontScale(2f);
        button.getLabel().setStyle(textLabelStyle);
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
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
