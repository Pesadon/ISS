package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.iss.screens.GameScreen;
import io.iss.utils.FontManager;

public abstract class GameState {
    protected final GameScreen screen;
    protected Stage stage;
    protected Viewport viewport;

    public GameState(GameScreen screen) {
        this.screen = screen;
        this.viewport = new FitViewport(1920, 1080);
        this.stage = new Stage(viewport);
    }

    public void render(float delta) {
        // First update the state
        update(delta);

        // clearing the screen
        ScreenUtils.clear(0, 0, 0, 0);

        // Then update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
    }

    public void update(float delta) {
        stage.act(delta);
    }

    protected FontManager getFontManager() {
        return FontManager.getInstance();
    }

    public abstract void enter();
    public abstract void exit();
}
