package io.iss.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class FontManager {
    // Singleton instance to ensure we only have one font manager
    private static FontManager instance;

    // Different font sizes for different purposes
    private BitmapFont titleFont;      // For large titles
    private BitmapFont menuFont;       // For menu items
    private BitmapFont regularFont;    // For regular text

    // Font sizes
    private static final int TITLE_SIZE = 48;
    private static final int MENU_SIZE = 32;
    private static final int REGULAR_SIZE = 24;

    private FontManager() {
        // Initialize fonts in the private constructor
        initializeFonts();
    }

    public static FontManager getInstance() {
        if (instance == null) {
            instance = new FontManager();
        }
        return instance;
    }

    private void initializeFonts() {
        // Create a font generator using your chosen font file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
            Gdx.files.internal("fonts/roboto/Roboto-Regular.ttf")
        );

        // Create parameter objects for different font sizes
        FreeTypeFontGenerator.FreeTypeFontParameter titleParameter =
            createFontParameter(TITLE_SIZE);
        FreeTypeFontGenerator.FreeTypeFontParameter menuParameter =
            createFontParameter(MENU_SIZE);
        FreeTypeFontGenerator.FreeTypeFontParameter regularParameter =
            createFontParameter(REGULAR_SIZE);

        // Generate the actual fonts
        titleFont = generator.generateFont(titleParameter);
        menuFont = generator.generateFont(menuParameter);
        regularFont = generator.generateFont(regularParameter);

        // Clean up the generator
        generator.dispose();
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter createFontParameter(int size) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = Color.WHITE;
        // parameter.borderWidth = 1;
        // parameter.borderColor = Color.BLACK;
        // parameter.shadowOffsetX = 2;
        // parameter.shadowOffsetY = 2;
        // parameter.shadowColor = new Color(0, 0, 0, 0.5f);
        return parameter;
    }

    // Getter methods for different font styles
    public BitmapFont getTitleFont() { return titleFont; }
    public BitmapFont getMenuFont() { return menuFont; }
    public BitmapFont getRegularFont() { return regularFont; }

    // Method to create Label styles with our fonts
    public Label.LabelStyle createTitleStyle() {
        return new Label.LabelStyle(titleFont, Color.WHITE);
    }

    public Label.LabelStyle createMenuStyle() {
        return new Label.LabelStyle(menuFont, Color.WHITE);
    }

    public Label.LabelStyle createRegularStyle() {
        return new Label.LabelStyle(regularFont, Color.WHITE);
    }

    // Clean up resources
    public void dispose() {
        titleFont.dispose();
        menuFont.dispose();
        regularFont.dispose();
    }
}
