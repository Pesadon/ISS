package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.iss.factory.StateType;
import io.iss.screens.GameScreen;
import io.iss.utils.GameAssetManager;

public class IntroState extends GameState {
    private final Image doorImage;

    public IntroState(GameScreen screen) {
        super(screen);
        doorImage = new Image(GameAssetManager.getInstance().get(GameAssetManager.DOOR_TEXTURE, Texture.class));
    }

    @Override
    public void enter() {
        // Let's center the door on the screen
        // We'll use the actual dimensions of your viewport for proper positioning
        float centerX = stage.getWidth() / 2;
        float centerY = stage.getHeight() / 2;

        // Set the door's position relative to its center
        // This ensures true centering regardless of the image size
        doorImage.setPosition(
            centerX - doorImage.getWidth() / 2,
            centerY - doorImage.getHeight() / 2
        );

        // Add click functionality to the door
        doorImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // When the door is clicked, we'll transition to the play state
                screen.setState(screen.getStateFactory().createState(StateType.PLAY));
            }
        });

        // Add the door to our stage
        stage.addActor(doorImage);

        // Set up input processing
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void exit() {
        stage.clear();
        Gdx.input.setInputProcessor(null);
    }
}
