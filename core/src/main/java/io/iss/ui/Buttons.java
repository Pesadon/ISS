package io.iss.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.iss.commands.MenuCommand;
import io.iss.ui.generators.ButtonAtlasGenerator;
import io.iss.utils.FontManager;

public class Buttons {
    private static float ANIMATION_DURATION;
    private static float BUTTON_PADDING;
    private final TextureAtlas buttonAtlas;

    public Buttons(float animationDuration, float buttonPadding) {
        ANIMATION_DURATION = animationDuration;
        BUTTON_PADDING = buttonPadding;
        buttonAtlas = ButtonAtlasGenerator.createButtonAtlas();
    }

    public Buttons() {
        ANIMATION_DURATION = 0.3f;
        BUTTON_PADDING = 20f;
        buttonAtlas = ButtonAtlasGenerator.createButtonAtlas();
    }

    public void createAnimatedButton(Table table, String text, final MenuCommand command) {
        TextButton button = new TextButton(text, getButtonStyle());

        // Add hover effect
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.addAction(Actions.sequence(
                    Actions.scaleTo(1.1f, 1.1f, 0.1f)
                ));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.addAction(Actions.sequence(
                    Actions.scaleTo(1f, 1f, 0.1f),
                    Actions.color(Color.WHITE, 0.2f)
                ));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Add click animation
                button.addAction(Actions.sequence(
                    Actions.scaleTo(0.9f, 0.9f, 0.1f),
                    Actions.scaleTo(1f, 1f, 0.1f),
                    Actions.run(command::execute)
                ));
            }
        });

        // Add button with animation
        button.setTransform(true);
        button.setOrigin(Align.center);
        button.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.parallel(
                Actions.fadeIn(ANIMATION_DURATION),
                Actions.moveBy(0, -20, ANIMATION_DURATION, Interpolation.swingOut)
            )
        ));

        table.add(button).pad(BUTTON_PADDING).width(200).row();
    }

    public TextButton.TextButtonStyle getButtonStyle() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = new TextureRegionDrawable(buttonAtlas.findRegion("button-up"));
        style.down = new TextureRegionDrawable(buttonAtlas.findRegion("button-down"));
        style.over = new TextureRegionDrawable(buttonAtlas.findRegion("button-hover"));

        style.font = FontManager.getInstance().getMenuFont();

        return style;
    }

    public void dispose() {
        buttonAtlas.dispose();
    }
}
