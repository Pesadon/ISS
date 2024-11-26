package io.iss.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameStage extends Stage {

    public GameStage() {
        super(new ScreenViewport());
        // Background
        Texture backgroundTexture = new Texture("backgrounds/background.jpg");
        Image backgroundImage = new Image(backgroundTexture);
        //backgroundImage.setFillParent(true);
        addActor(backgroundImage);
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
