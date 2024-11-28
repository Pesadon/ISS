package io.iss.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.iss.controller.StageManager;


public class IntroStage extends Stage {

    public IntroStage(final StageManager stageManager, Skin skin) {
        Texture doorTexture = new Texture(Gdx.files.internal("images/door.png"));

        Drawable doorDrawable = new TextureRegionDrawable(new TextureRegion(doorTexture));

        Image hoverImage = new Image(doorDrawable);
        hoverImage.setPosition(getWidth() / 2f, 20);

        hoverImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.switchStage("game");
            }
        });

        addActor(hoverImage);
    }
}
