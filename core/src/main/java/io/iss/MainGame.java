package io.iss;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.iss.screens.GameScreen;
import io.iss.utils.FontManager;
import io.iss.utils.GameAssetManager;

public class MainGame extends Game {
    private Skin skin;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        GameAssetManager.getInstance().loadInitialAssets();
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        batch.begin();
        super.render();
        batch.end();
    }

    public Skin getSkin() {
        return skin;
    }

    @Override
    public void resize(int width, int height) {
        // stageManager.getActiveStage().getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        skin.dispose();
        batch.dispose();
        GameAssetManager.getInstance().dispose();
        FontManager.getInstance().dispose();
        super.dispose();
    }
}
