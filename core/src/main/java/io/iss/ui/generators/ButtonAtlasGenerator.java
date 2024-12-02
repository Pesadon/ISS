package io.iss.ui.generators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ButtonAtlasGenerator {
    private static final int BUTTON_WIDTH = 350;
    private static final int BUTTON_HEIGHT = 50;
    private static final int CORNER_RADIUS = 10;

    public static TextureAtlas createButtonAtlas() {
        // Creating buttons with a professional color scheme
        // Normal state: dark with subtle gradient
        Texture upTexture = createButtonTexture(
            new Color(0.2f, 0.2f, 0.25f, 1),    // Dark base color
            new Color(0.25f, 0.25f, 0.3f, 1),   // Slightly lighter top
            2
        );

        // Pressed state: slightly darker
        Texture downTexture = createButtonTexture(
            new Color(0.15f, 0.15f, 0.2f, 1),   // Darker base when pressed
            new Color(0.2f, 0.2f, 0.25f, 1),    // Darker top when pressed
            2
        );

        // Hover state: just slightly lighter than normal
        // We've reduced the brightness to create a more subtle effect
        Texture hoverTexture = createButtonTexture(
            new Color(0.22f, 0.22f, 0.27f, 1),   // Just slightly lighter than normal
            new Color(0.27f, 0.27f, 0.32f, 1),   // Just slightly lighter top
            2
        );

        TextureAtlas atlas = new TextureAtlas();
        atlas.addRegion("button-up", upTexture, 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        atlas.addRegion("button-down", downTexture, 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        atlas.addRegion("button-hover", hoverTexture, 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);

        return atlas;
    }

    private static Texture createButtonTexture(Color baseColor, Color topColor, int borderSize) {
        Pixmap pixmap = new Pixmap(BUTTON_WIDTH, BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setFilter(Pixmap.Filter.BiLinear);

        // Create subtle border
        Color borderColor = new Color(0.3f, 0.3f, 0.35f, 1);
        pixmap.setColor(borderColor);
        fillRoundedRect(pixmap, 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, CORNER_RADIUS);

        // Draw main button body with subtle gradient
        for (int y = borderSize; y < BUTTON_HEIGHT - borderSize; y++) {
            float ratio = (float)(y - borderSize) / (BUTTON_HEIGHT - borderSize * 2);
            Color currentColor = baseColor.cpy().lerp(topColor, ratio);
            pixmap.setColor(currentColor);

            for (int x = borderSize; x < BUTTON_WIDTH - borderSize; x++) {
                if (isInsideRoundedRect(x - borderSize, y - borderSize,
                    BUTTON_WIDTH - borderSize * 2, BUTTON_HEIGHT - borderSize * 2,
                    CORNER_RADIUS - borderSize)) {
                    pixmap.drawPixel(x, y);
                }
            }
        }

        // Add a very subtle top highlight
        Color highlightColor = new Color(1, 1, 1, 0.1f);
        pixmap.setColor(highlightColor);
        for (int y = borderSize; y < BUTTON_HEIGHT/4; y++) {
            float alpha = 1 - (float)y / (BUTTON_HEIGHT/4);
            pixmap.setColor(1, 1, 1, alpha * 0.1f);
            for (int x = borderSize * 2; x < BUTTON_WIDTH - borderSize * 2; x++) {
                if (isInsideRoundedRect(x - borderSize * 2, y - borderSize,
                    BUTTON_WIDTH - borderSize * 4, BUTTON_HEIGHT - borderSize * 2,
                    CORNER_RADIUS - borderSize)) {
                    pixmap.drawPixel(x, y);
                }
            }
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /**
     * Disegna un rettangolo con angoli arrotondati riempito.
     * Questo metodo utilizza l'algoritmo del punto medio per creare angoli arrotondati smussati.
     */
    private static void fillRoundedRect(Pixmap pixmap, int x, int y, int width, int height, int radius) {
        // Disegna il rettangolo principale
        pixmap.fillRectangle(x + radius, y, width - 2 * radius, height);
        pixmap.fillRectangle(x, y + radius, width, height - 2 * radius);

        // Disegna i quattro angoli arrotondati
        fillCircleQuarter(pixmap, x + radius, y + radius, radius, 1); // Top-left
        fillCircleQuarter(pixmap, x + width - radius, y + radius, radius, 2); // Top-right
        fillCircleQuarter(pixmap, x + width - radius, y + height - radius, radius, 3); // Bottom-right
        fillCircleQuarter(pixmap, x + radius, y + height - radius, radius, 4); // Bottom-left
    }

    /**
     * Disegna un quarto di cerchio riempito.
     * Il parametro quarter determina quale quarto disegnare (1-4, partendo da in alto a sinistra,
     * procedendo in senso orario).
     */
    private static void fillCircleQuarter(Pixmap pixmap, int centerX, int centerY, int radius, int quarter) {
        int x = radius;
        int y = 0;
        int decisionOver2 = 1 - x;

        while (y <= x) {
            switch (quarter) {
                case 1: // Top-left
                    pixmap.drawLine(centerX - x, centerY - y, centerX, centerY - y);
                    pixmap.drawLine(centerX - y, centerY - x, centerX, centerY - x);
                    break;
                case 2: // Top-right
                    pixmap.drawLine(centerX, centerY - y, centerX + x, centerY - y);
                    pixmap.drawLine(centerX, centerY - x, centerX + y, centerY - x);
                    break;
                case 3: // Bottom-right
                    pixmap.drawLine(centerX, centerY + y, centerX + x, centerY + y);
                    pixmap.drawLine(centerX, centerY + x, centerX + y, centerY + x);
                    break;
                case 4: // Bottom-left
                    pixmap.drawLine(centerX - x, centerY + y, centerX, centerY + y);
                    pixmap.drawLine(centerX - y, centerY + x, centerX, centerY + x);
                    break;
            }
            y++;
            if (decisionOver2 <= 0) {
                decisionOver2 += 2 * y + 1;
            } else {
                x--;
                decisionOver2 += 2 * (y - x) + 1;
            }
        }
    }

    /**
     * Determina se un punto si trova all'interno di un rettangolo con angoli arrotondati.
     * Questo metodo è utilizzato per sapere dove disegnare i pixel per creare gli angoli arrotondati.
     */
    private static boolean isInsideRoundedRect(int px, int py, int width, int height, int radius) {
        // Se il punto è fuori dal rettangolo, ritorna false
        if (px < 0 || px >= width || py < 0 || py >= height) {
            return false;
        }

        // Se il punto non è in una delle zone degli angoli, è sicuramente dentro
        if (px >= radius && px <= width - radius) {
            return true;
        }
        if (py >= radius && py <= height - radius) {
            return true;
        }

        // Controlla se il punto è dentro uno degli angoli arrotondati
        int dx, dy;
        if (px < radius) {
            if (py < radius) { // Angolo in alto a sinistra
                dx = radius - px;
                dy = radius - py;
            } else { // Angolo in basso a sinistra
                dx = radius - px;
                dy = py - (height - radius);
            }
        } else {
            if (py < radius) { // Angolo in alto a destra
                dx = px - (width - radius);
                dy = radius - py;
            } else { // Angolo in basso a destra
                dx = px - (width - radius);
                dy = py - (height - radius);
            }
        }

        // Se la distanza dal centro dell'angolo è minore del raggio, il punto è dentro
        return dx * dx + dy * dy <= radius * radius;
    }
}
