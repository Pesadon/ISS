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
import io.iss.dialogue.context.DialogueLoader;
import io.iss.factory.StateType;
import io.iss.objects.GameObject;
import io.iss.objects.InteractiveObject;
import io.iss.screens.GameScreen;
import io.iss.ui.Inventory;
import io.iss.ui.PauseMenu;
import io.iss.utils.FontManager;
import io.iss.utils.GameAssetManager;

public class IntroState extends GameState {
    private final Image doorImage;
    private final Image danteImage;
    private final Label danteAdvice;

    private final PauseMenu pauseMenu;
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;

    public IntroState(GameScreen screen) {
        super(screen);

        dialogueLoader = new DialogueLoader(GameAssetManager.DIALOGUES_JSON);
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

        //stage.addActor(table);
        //stage.addActor(danteImage);

        initPauseButton();

        GameObject notebook = new GameObject("sword", new Texture("images/notebook.png"));
        notebook.setPosition(200, 100);
        notebook.setSize(100, 100);
        stage.addActor(notebook);

        GameObject key = new GameObject("key", new Texture("images/key.jpg"));
        key.setPosition(400, 100);
        key.setSize(100, 100);
        stage.addActor(key);

        //TODO: why do we need this first call of dialogue scene to make the actual dialogue work?
        dialogueContext.startScene(dialogueLoader.getScene("its_dante"));

        GameObject littleDante = new GameObject("dante", new Texture("images/Dante.jpg"), () -> {
            Inventory.getInstance().hide();
            stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());
            dialogueContext.startScene(dialogueLoader.getScene("its_dante"), () -> {
                dialogueContext.getDialogueUI().getDialogueBox().remove();
                Inventory.getInstance().show();
            });
        });

        littleDante.setPosition(600, 100);
        littleDante.setSize(100, 100);
        stage.addActor(littleDante);

        InteractiveObject door = new InteractiveObject(
            new Texture("images/door.png"), "key",
            () -> {
            Inventory.getInstance().hide();
            stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());
            dialogueContext.startScene(dialogueLoader.getScene("locked_door"), () -> {
                dialogueContext.getDialogueUI().getDialogueBox().remove();
                Inventory.getInstance().show();
            });
            },
            () -> {
                screen.setState(screen.getStateFactory().createState(StateType.MENU));
            });

        door.setPosition(
            centerX - doorImage.getWidth() / 2,
            centerY - doorImage.getHeight() / 2
        );
        stage.addActor(door);

        stage.addActor(Inventory.getInstance().getInventoryBar());

        // Set up input processing
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void update(float delta) {
        dialogueContext.update(delta);
        super.update(delta);
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
