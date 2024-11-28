package io.iss.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.iss.controller.StageManager;

public class MenuStage extends Stage {
    public MenuStage(final StageManager stageManager, Skin skin) {

        TextButton newGameButton = new TextButton("New game", skin);
        TextButton continueButton = new TextButton("Continue", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stageManager.switchStage("intro"); // Change to IntroStage
            }
        });

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(newGameButton).width(200).pad(10);
        table.row();
        table.add(continueButton).width(200).pad(10);
        table.row();
        table.add(settingsButton).width(200).pad(10);
        table.row();
        table.add(exitButton).width(200).pad(10);

        addActor(table);
    }
}
