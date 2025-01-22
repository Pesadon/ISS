package io.iss.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniGame {
    private final Stage stage;
    private final MiniGameListener listener;
    private Window miniGameWindow;
    private Label instructionLabel;
    private Label timerLabel;
    private Label resultLabel; // Label for game result
    private TextButton redButton, greenButton, blueButton;
    private Image backgroundImage;
    private static final float MINI_GAME_TIME_LIMIT = 15.0f; // 15 seconds
    private float timeElapsed;
    private boolean isMiniGameActive;
    private boolean gameCompleted; // Tracks if the game was successfully completed
    private int currentRound;
    private int totalRounds; // Number of rounds to complete
    private List<String> instructions;
    private int score;
    private final Random random = new Random();

    public MiniGame(Stage stage, MiniGameListener listener) {
        this.stage = stage;
        this.listener = listener;
        this.isMiniGameActive = false;
        this.gameCompleted = false; // Initialize as false
        this.instructions = new ArrayList<>();
        this.score = 0;
    }

    public void start() {
        if (gameCompleted) {
            System.out.println("The game has already been completed successfully. Cannot restart.");
            return;
        }

        Skin skin = new Skin(Gdx.files.internal("assets/ui/uiskin.json"));
        Texture backgroundTexture = new Texture(Gdx.files.internal("assets/images/monitor.png"));
        backgroundImage = new Image(backgroundTexture);

        // Set size and position of the background image
        backgroundImage.setSize(1280, 1049);
        backgroundImage.setPosition(
            stage.getWidth() / 2 - 1280 / 2,
            stage.getHeight() / 2 - 1049 / 2
        );

        isMiniGameActive = true;
        timeElapsed = 0;
        currentRound = 0;
        instructions.clear();
        score = 0;

        // Randomize total rounds between 5 and 10
        totalRounds = 10 + random.nextInt(6); // Generates a number between 5 and 10

        generateInstructions();

        miniGameWindow = new Window("Reflex Mini Game", skin);

        // Inner dimensions of the monitor based on the image
        int monitorInnerWidth = 950;  // Width of the green area
        int monitorInnerHeight = 577; // Height of the green area

        // Set the window's size and position
        miniGameWindow.setSize(monitorInnerWidth, monitorInnerHeight);
        miniGameWindow.setPosition(
            stage.getWidth() / 2 - monitorInnerWidth / 2,
            (stage.getHeight() / 2 - monitorInnerHeight / 2) + 91
        );

        instructionLabel = new Label("", skin);
        instructionLabel.setFontScale(2.0f);

        timerLabel = new Label("Time: " + MINI_GAME_TIME_LIMIT, skin);

        resultLabel = new Label("", skin); // Initialize result label
        resultLabel.setFontScale(2.0f);
        resultLabel.setVisible(false); // Initially hide the label

        redButton = new TextButton("Red", skin);
        greenButton = new TextButton("Green", skin);
        blueButton = new TextButton("Blue", skin);

        // Set button text colors to match their labels
        redButton.getLabel().setColor(Color.RED);
        greenButton.getLabel().setColor(Color.GREEN);
        blueButton.getLabel().setColor(Color.BLUE);

        // Set button sizes
        redButton.setSize(200, 100);
        greenButton.setSize(200, 100);
        blueButton.setSize(200, 100);

        // Button click listeners
        redButton.addListener(createButtonListener("Red"));
        greenButton.addListener(createButtonListener("Green"));
        blueButton.addListener(createButtonListener("Blue"));

        Table buttonTable = new Table();
        buttonTable.add(redButton).size(100, 50).pad(50);
        buttonTable.add(greenButton).size(100, 50).pad(50);
        buttonTable.add(blueButton).size(100, 50).pad(50);

        miniGameWindow.add(instructionLabel).padBottom(20).row();
        miniGameWindow.add(timerLabel).padBottom(20).row();
        miniGameWindow.add(buttonTable).padBottom(20).row();
        miniGameWindow.add(resultLabel).padTop(20); // Add result label at the bottom

        stage.addActor(backgroundImage);
        stage.addActor(miniGameWindow);

        showNextInstruction();
    }

    private void generateInstructions() {
        String[] possibleColors = {"Red", "Green", "Blue"};
        for (int i = 0; i < totalRounds; i++) {
            instructions.add(possibleColors[random.nextInt(possibleColors.length)]);
        }
    }

    private void showNextInstruction() {
        if (currentRound < instructions.size()) {
            String textColor = instructions.get(currentRound);
            String fontColor = getRandomDifferentColor(textColor);

            instructionLabel.setText("Press: " + textColor);
            instructionLabel.setColor(getColorFromName(fontColor)); // Set the font color
        } else {
            end(true);
        }
    }

    private String getRandomDifferentColor(String excludedColor) {
        List<String> colors = new ArrayList<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.remove(excludedColor); // Remove the excluded color
        return colors.get(random.nextInt(colors.size()));
    }

    private Color getColorFromName(String colorName) {
        switch (colorName) {
            case "Red":
                return Color.RED;
            case "Green":
                return Color.GREEN;
            case "Blue":
                return Color.BLUE;
            default:
                return Color.WHITE;
        }
    }

    private ClickListener createButtonListener(final String color) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isMiniGameActive) return;

                if (color.equals(instructions.get(currentRound))) {
                    score++;
                    currentRound++;
                    showNextInstruction();
                } else {
                    end(false); // Game over on incorrect click
                }
            }
        };
    }

    public void update(float delta) {
        if (!isMiniGameActive) return;

        timeElapsed += delta;
        timerLabel.setText("Time: " + Math.max(0, MINI_GAME_TIME_LIMIT - timeElapsed));

        if (timeElapsed > MINI_GAME_TIME_LIMIT) {
            end(false);
        }
    }

    private void end(boolean success) {
        isMiniGameActive = false;

        if (success) {
            resultLabel.setText("Success!");
            resultLabel.setColor(Color.GREEN);
            gameCompleted = true; // Mark the game as completed
        } else {
            resultLabel.setText("Game Over");
            resultLabel.setColor(Color.RED);
        }

        resultLabel.setVisible(true); // Show the result label
        redButton.setDisabled(true);
        greenButton.setDisabled(true);
        blueButton.setDisabled(true);

        // Close the game window after 3 seconds
        Gdx.app.postRunnable(() -> {
            try {
                Thread.sleep(3000); // Delay for 3 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            miniGameWindow.remove();
            backgroundImage.remove(); // Remove background image as well
            if (listener != null) {
                listener.onMiniGameEnd(success);
            }
        });
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }

    public interface MiniGameListener {
        void onMiniGameEnd(boolean success);
    }
}
