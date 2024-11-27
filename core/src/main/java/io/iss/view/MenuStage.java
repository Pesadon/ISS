package io.iss.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.iss.controller.StageManager;

public class MenuStage extends Stage {
    public MenuStage(final StageManager stageManager, Skin skin) {
        TextButton playButton = new TextButton("New game", skin);
        playButton.setPosition(300, 300);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                stageManager.switchStage("game"); // Change to GameStage
            }
        });

        addActor(playButton);
    }
}
