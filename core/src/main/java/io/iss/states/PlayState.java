package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import io.iss.screens.GameScreen;
import io.iss.utils.FontManager;
import io.iss.factory.StateType;
import io.iss.screens.GameScreen;
import io.iss.utils.GameAssetManager;

public class PlayState extends GameState {
    private int currentIndex = 0;
    private final Array<String> textList = new Array<>();
    private Label textLabel;

    public PlayState(GameScreen screen) {
        super(screen);
    }

    @Override
    public void enter() {
        Texture backgroundTexture = new Texture("backgrounds/background.jpg");
        Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setFillParent(true); // Make the image fill the stage
        stage.addActor(backgroundImage);

        // list of dialogues - later from json
        textList.add("First dialogue");
        textList.add("Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue Second dialogue");
        textList.add("Third dialogue");
        textList.add("Fourth dialogue");

        // Create the label to display text
        textLabel = new Label(textList.get(currentIndex), screen.getGame().getSkin());
        textLabel.setStyle(FontManager.getInstance().createRegularStyle());
        textLabel.setColor(Color.BLACK);
        textLabel.setWrap(true);
        textLabel.setAlignment(1);

        // Create the button to navigate through the text
        TextButton button = new TextButton("Next", screen.getGame().getSkin());
        button.getLabel().setStyle(FontManager.getInstance().createRegularStyle());
        button.pad(10f);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            if (currentIndex < textList.size - 1) {
                currentIndex++;
                textLabel.setText(textList.get(currentIndex));
            } else {
                button.setText("Next scene");
            }
            }
        });

        // Create a table to act as a "text box"
        Table textBox = new Table();
        Drawable background = screen.getGame().getSkin().newDrawable("white", Color.valueOf("#FFFACD")); // Pale yellow

        textBox.setBackground(background);
        textBox.setSize(viewport.getScreenWidth() / 1.2f, viewport.getScreenHeight() / 4f);
        textBox.setPosition(viewport.getScreenWidth() / 2f - textBox.getWidth() / 2f, 20);

        // Add the label and button to the table
        textBox.add(textLabel).expand().fill().pad(10f).row();
        textBox.add(button).pad(10f);

        // Add actors to the stage
        stage.addActor(textBox);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void exit() {
        Gdx.input.setInputProcessor(null);
    }
}
