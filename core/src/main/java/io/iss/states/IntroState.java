package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.iss.dialogue.context.DialogueContext;
import io.iss.factory.StateType;
import io.iss.screens.GameScreen;
import io.iss.ui.PauseMenu;
import io.iss.utils.FontManager;
import io.iss.utils.GameAssetManager;

public class IntroState extends GameState {
    private final Image doorImage;
    private final Image danteImage;
    private final Label danteAdvice;

    private final PauseMenu pauseMenu;
    private final DialogueContext dialogueContext;

    public IntroState(GameScreen screen) {
        super(screen);

        dialogueContext = new DialogueContext(screen, stage);
        pauseMenu = new PauseMenu(screen, stage, dialogueContext);

        doorImage = new Image(GameAssetManager.getInstance().get(GameAssetManager.DOOR_TEXTURE, Texture.class));
        danteImage = new Image(GameAssetManager.getInstance().get(GameAssetManager.DANTE_TEXTURE, Texture.class));
        danteAdvice = new Label("All hope abandon ye who enter here", FontManager.getInstance().createRegularStyle());
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
            screen.setState(screen.getStateFactory().createState(StateType.MENU));
            }
        });

        danteImage.setScale(0.3f);
        danteImage.setPosition(
            centerX - ((danteImage.getWidth() * 0.3f) / 2),
            centerY - ((danteImage.getHeight() * 0.3f) / 2)
        );

        Table table = new Table();
        table.setFillParent(true);
        table.add(danteAdvice).padBottom(30).row();
        table.add(doorImage).row();

        // Add the door to our stage
        // stage.addActor(doorImage);

        stage.addActor(table);
        stage.addActor(danteImage);

        initPauseButton();

        // Set up input processing
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void exit() {
        stage.clear();
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }

    public void initPauseButton() {
        // Load the image for the button (you can replace "pauseImage" with your image name)
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("assets/ui/pausebutton.png")));
        buttonStyle.down = buttonStyle.up; // Optionally, add a "pressed" state image if needed

        // Create the image button with the style
        ImageButton pauseButton = new ImageButton(buttonStyle);

        // Make the button square (adjust the size to your liking)
        float buttonSize = 80f; // Set the size of the button
        pauseButton.setSize(buttonSize, buttonSize);

        // Position the button in the top-left corner
        float buttonPadding = 20f; // Padding from the edges
        pauseButton.setPosition(buttonPadding, Gdx.graphics.getHeight() - pauseButton.getHeight() - buttonPadding);

        // Add the listener to handle the button click
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseMenu.showPauseMenu(); // Show the pause menu when the button is clicked
            }
        });

        // Add the button to the stage
        stage.addActor(pauseButton);
    }
}
