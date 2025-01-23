package io.iss.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.iss.commands.MainMenuCommand;
import io.iss.dialogue.context.DialogueContext;
import io.iss.screens.GameScreen;
import io.iss.states.GameState;
import io.iss.utils.FontManager;
import io.iss.utils.JournalManager;

public class PauseMenu {
    private Table pauseMenuTable;
    private Window warningDialog;
    private DialogueContext dialogueContext;
    private Stage stage;
    private GameScreen screen;
    private GameState state;
    private Buttons buttons;

    public PauseMenu(GameScreen screen, GameState state, Stage stage, DialogueContext dialogueContext) {
        this.screen = screen;
        this.state = state;
        this.stage = stage;
        this.dialogueContext = dialogueContext;
        this.buttons = new Buttons();
    }

    public void showPauseMenu() {
        Skin skin = screen.getGame().getSkin();
        dialogueContext.pause();
        // Create a blocking layer to intercept input outside the pause menu
        Actor blockingLayer = new Actor();
        blockingLayer.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        blockingLayer.setName("blockingLayer"); // Add a name for easier removal
        blockingLayer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Consume all touch events outside the pause menu
                return true;
            }
        });

        // Add the blocking layer to the stage
        stage.addActor(blockingLayer);

        // Create the pause menu table
        pauseMenuTable = new Table();
        pauseMenuTable.setFillParent(true);

        // Set background color
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.7f)); // Semi-transparent black
        pixmap.fill();
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        pauseMenuTable.setBackground(backgroundDrawable);


        Label titleLabel = new Label("Pause Menu", FontManager.getInstance().createTitleStyle());
        titleLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(0.3f)
        ));
        pauseMenuTable.add(titleLabel).padBottom(80).row();


        buttons.createAnimatedButton(pauseMenuTable, "Resume", this::resumeGame);
        buttons.createAnimatedButton(pauseMenuTable, "Save", this::saveGame);
        buttons.createAnimatedButton(pauseMenuTable, "Main menu", ()->showWarningDialog(0));
        buttons.createAnimatedButton(pauseMenuTable, "Exit", ()->showWarningDialog(1));

        // Add the pause menu to the stage
        stage.addActor(pauseMenuTable);

        // Disable game input while pause menu is active
        Gdx.input.setInputProcessor(stage);
    }

    private void resumeGame() {
        // Remove the pause menu table
        if (pauseMenuTable != null) {
            pauseMenuTable.remove();
            pauseMenuTable = null;
            dialogueContext.resume();
        }

        if(warningDialog != null) {
            warningDialog.remove();
        }

        // Remove the blocking layer
        Actor blockingLayer = stage.getRoot().findActor("blockingLayer");
        if (blockingLayer != null) {
            blockingLayer.remove();
        }

        // Re-enable game input
        Gdx.input.setInputProcessor(stage);
    }

    private void showWarningDialog(int sourceMethod){
        Skin skin=screen.getGame().getSkin();

        warningDialog=new Window("", skin);
        warningDialog.setModal(true);
        warningDialog.setMovable(false);

        // Add the warning message
        Label warningLabel = new Label("If you quit without saving, your progress will be lost.", FontManager.getInstance().createRegularStyle());
        warningLabel.setWrap(true);
        warningDialog.add(warningLabel).width(400).pad(20).row();

        // Add the buttons
        Table buttonTable = new Table();

        buttons.createAnimatedButton(buttonTable,"Continue", (sourceMethod==0 ? (new MainMenuCommand(screen)) : (Gdx.app::exit)));
        buttons.createAnimatedButton(buttonTable, "Save", this::saveGame);
        buttons.createAnimatedButton(buttonTable, "Cancel", this::resumeGame);

        // Add the button table to the dialog
        warningDialog.add(buttonTable).padTop(20).row();

        // Position the dialog and add it to the stage
        warningDialog.pack();
        warningDialog.setSize(warningDialog.getWidth() + 50, warningDialog.getHeight() + 50); // Add padding

        warningDialog.setPosition(
            (Gdx.graphics.getWidth() - warningDialog.getWidth()) / 2,
            (Gdx.graphics.getHeight() - warningDialog.getHeight()) / 2
        );
        stage.addActor(warningDialog);
    }

    private void saveGame(){
        Inventory.getInstance().save();
        JournalManager.getInstance().save();
        screen.getStateFactory().saveState(state.getType());
    }
}
