package io.iss.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.iss.view.GameStage;
import io.iss.view.MenuStage;

public class StageFactory {
    private Skin skin;

    public StageFactory(Skin skin) {
        this.skin = skin;
    }

    public Stage createStage(String name) {
        return switch (name) {
            case "menu" -> new MenuStage(StageManager.getInstance(), skin);
            case "game" -> new GameStage(StageManager.getInstance(), skin);
            default -> throw new IllegalArgumentException("Stage " + name + " non supportato.");
        };
    }
}
