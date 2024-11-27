package io.iss.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.HashMap;

public class StageManager {
    private static StageManager instance; // Singleton
    private HashMap<String, Stage> stages = new HashMap<>();
    private Stage activeStage;

    private StageManager() {}

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public void addStage(String name, Stage stage) {
        stages.put(name, stage);
    }

    public void switchStage(String name) {
        if (stages.containsKey(name)) {
            activeStage = stages.get(name);
            Gdx.input.setInputProcessor(activeStage); // Aggiorna l'input processor
        } else {
            throw new IllegalArgumentException("Stage " + name + " non trovato.");
        }
    }

    public Stage getActiveStage() {
        return activeStage;
    }

    public void act(float delta) {
        if (activeStage != null) {
            activeStage.act(delta);
        }
    }

    public void draw() {
        if (activeStage != null) {
            activeStage.draw();
        }
    }

    public void dispose() {
        for (Stage stage : stages.values()) {
            stage.dispose();
        }
    }
}
