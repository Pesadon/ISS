package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import io.iss.dialogue.context.DialogueContext;
import io.iss.dialogue.context.DialogueLoader;
import io.iss.factory.StateType;
import io.iss.minigames.ColorMiniGame;
import io.iss.objects.GameObject;
import io.iss.screens.GameScreen;
import io.iss.ui.Inventory;
import io.iss.utils.JournalManager;
import io.iss.ui.JournalWindow;
import io.iss.ui.PauseMenu;
import io.iss.utils.GameAssetManager;
import io.iss.utils.InteractiveArea;

public class TestRoom2State extends GameState implements ColorMiniGame.MiniGameListener {

    private final PauseMenu pauseMenu;
    private final JournalWindow journalWindow;
    private final DialogueContext dialogueContext;
    private final DialogueLoader dialogueLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private ColorMiniGame miniGame;
    private OrthographicCamera camera;
    private InteractiveArea[] interactiveAreas;
    private ShapeRenderer shapeRenderer;
    public boolean isJournalOpen;
    public boolean isComputerOpen;
    public boolean isInteractionActive;

    public TestRoom2State(GameScreen screen) {
        super(screen);

        type = StateType.TEST_ROOM2;

        dialogueLoader = new DialogueLoader(GameAssetManager.DIALOGUES_JSON);
        dialogueContext = new DialogueContext(screen, stage);

        pauseMenu = new PauseMenu(screen, this, stage, dialogueContext, this::enableActiveInteraction);
        initPauseButton();

        journalWindow = new JournalWindow(screen, stage, dialogueContext, this::onJournalClick);
        initJournalButton();

        map = new TmxMapLoader().load("tilemap/office.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        MapLayer objectLayer = map.getLayers().get("Layer");

        MapObjects objects = objectLayer.getObjects();

        MapObject bookshelfObject = objects.get("Bookshelf");
        MapObject doorObject = objects.get("Door");
        MapObject journalObject = objects.get("Journal");
        MapObject computerObject = objects.get("Computer");

        interactiveAreas = new InteractiveArea[4];
        shapeRenderer = new ShapeRenderer();

        PolygonMapObject polygonObject = (PolygonMapObject) bookshelfObject;
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();
        interactiveAreas[0] = new InteractiveArea(vertices, this::onBookshelfClick);

        polygonObject = (PolygonMapObject) doorObject;
        vertices = polygonObject.getPolygon().getTransformedVertices();
        interactiveAreas[1] = new InteractiveArea(vertices, this::onDoorClick);

        polygonObject = (PolygonMapObject) journalObject;
        vertices = polygonObject.getPolygon().getTransformedVertices();
        interactiveAreas[2] = new InteractiveArea(vertices, this::onJournalClick);

        polygonObject = (PolygonMapObject) computerObject;
        vertices = polygonObject.getPolygon().getTransformedVertices();
        interactiveAreas[3] = new InteractiveArea(vertices, this::onComputerClick);

        miniGame = new ColorMiniGame(stage, this);

        isJournalOpen = false;
        isComputerOpen = false;
        isInteractionActive = true;
    }

    public void enableActiveInteraction() {
        this.isInteractionActive = true;
    }

    public void onJournalClick() {
        if (!isJournalOpen) {
            journalWindow.showJournalWindow();
        } else {
            journalWindow.resumeGame();
        }
        isInteractionActive = isJournalOpen;
        isJournalOpen = !isJournalOpen;
    }

    public void onDoorClick() {
        if(Inventory.getInstance().isCollected("key") && Inventory.getInstance().getSelectedItem() != null && Inventory.getInstance().getSelectedItem().getId().equals("key")) {
            screen.setState(screen.getStateFactory().createState(StateType.MENU));
        } else {
            JournalManager.getInstance().appendTextWithId("LockedDoor", "The door to my office is locked, but I don't remember locking it.\nI should start looking for the key. Where could it be?");
            stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());
            dialogueContext.startScene(dialogueLoader.getScene("locked_door"), () -> {
                dialogueContext.getDialogueUI().getDialogueBox().remove();
            });
        }
    }

    public void onBookshelfClick() {
        stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());
        dialogueContext.startScene(dialogueLoader.getScene("bookshelf"), () -> {
            dialogueContext.getDialogueUI().getDialogueBox().remove();
        });
    }

    public void onComputerClick() {
        if(!Inventory.getInstance().isCollected("key")) {
            if (!isComputerOpen) {
                isComputerOpen = true;
                miniGame.start();
            }
        }
    }

    @Override
    public void enter() {
        Table table = new Table();
        table.setFillParent(true);

        if(!JournalManager.getInstance().getAddedIds().contains("TestRoom2Enter")) {
            JournalManager.getInstance().appendTextWithId("TestRoom2Enter", "Looks like I'm in my office, I should investigate to understand what happened");
            stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());
            dialogueContext.startScene(dialogueLoader.getScene("office_scene"));
        }

        stage.addActor(Inventory.getInstance().getInventoryBar());

        Inventory.getInstance().show();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // First update the state
        update(delta);

        // clearing the screen
        ScreenUtils.clear(0, 0, 0, 0);

        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        if (isInteractionActive) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            for (InteractiveArea area : interactiveAreas) {
                area.setHovered(area.contains(mousePos.x, mousePos.y));
                area.render(shapeRenderer);
                area.checkClick(mousePos.x, mousePos.y);
            }
            shapeRenderer.end();
        }

        // Then update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void update(float delta) {
        dialogueContext.update(delta);
        if (miniGame != null) {
            miniGame.update(delta);
        }
        super.update(delta);
    }

    @Override
    public void exit() {
        stage.clear();
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }


    @Override
    public void onMiniGameEnd(boolean success) {
        if (success) {
            stage.addActor(dialogueContext.getDialogueUI().getDialogueBox());
            dialogueContext.startScene(dialogueLoader.getScene("mini_game_win"), () -> {
                dialogueContext.getDialogueUI().getDialogueBox().remove();
                JournalManager.getInstance().appendTextWithId("TestRoom2Dante", "Now that I have the key I'll finally be able to open the door");
                Inventory.getInstance().addItem(new GameObject("key", new Texture("images/key.png")));
                Inventory.getInstance().show();
            });
        } else {
            System.out.println("MiniGame failed.");
        }
        isComputerOpen = false;
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
                isInteractionActive = false;
            }
        });

        // Add the button to the stage
        stage.addActor(pauseButton);
    }

    public void initJournalButton() {
        // Load the image for the button (you can replace "pauseImage" with your image name)
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("assets/images/notebook.png")));
        buttonStyle.down = buttonStyle.up; // Optionally, add a "pressed" state image if needed

        // Create the image button with the style
        ImageButton journalButton = new ImageButton(buttonStyle);

        // Make the button square (adjust the size to your liking)
        float buttonSize = 80f; // Set the size of the button
        journalButton.setSize(buttonSize, buttonSize);

        // Position the button in the top-right corner
        float buttonPadding = 20f; // Padding from the edges
        journalButton.setPosition(Gdx.graphics.getWidth() - buttonSize - buttonPadding, Gdx.graphics.getHeight() - journalButton.getHeight() - buttonPadding);

        // Add the listener to handle the button click
        journalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onJournalClick(); // Show the pause menu when the button is clicked
            }
        });

        // Add the button to the stage
        stage.addActor(journalButton);
    }
}
