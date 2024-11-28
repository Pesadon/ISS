package io.iss;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import io.iss.controller.StageFactory;
import io.iss.controller.StageManager;

public class Main extends ApplicationAdapter {
    private Skin skin;
    private StageManager stageManager;

    @Override
    public void create() {
        // TODO why do we set the fullscreen mode here and not in the Lwjgl3Launcher?
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        stageManager = StageManager.getInstance();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        StageFactory stageFactory = new StageFactory(skin);

        stageManager.addStage("menu", stageFactory.createStage("menu"));
        stageManager.addStage("intro", stageFactory.createStage("intro"));
        stageManager.addStage("game", stageFactory.createStage("game"));

        stageManager.switchStage("menu");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        stageManager.act(Gdx.graphics.getDeltaTime());
        stageManager.draw();
    }

    @Override
    public void resize(int width, int height) {
        stageManager.getActiveStage().getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stageManager.dispose();
        skin.dispose();
    }
}
