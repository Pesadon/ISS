package io.iss.ui.generators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class PanelGenerator {
    public static Texture createMenuPanel(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        // Define our colors for a more sophisticated look
        Color panelBaseColor = new Color(0.1f, 0.12f, 0.15f, 0.95f);  // Dark, slightly blue-ish
        Color panelEdgeColor = new Color(0.15f, 0.17f, 0.2f, 0.95f);  // Lighter edge
        Color highlightColor = new Color(1, 1, 1, 0.2f);              // Subtle white highlight
        Color shadowColor = new Color(0, 0, 0, 0.4f);                 // Soft shadow

        // Create the main panel gradient
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Calculate distances for various effects
                float centerDistX = Math.abs(x - width/2f) / (width/2f);
                float centerDistY = Math.abs(y - height/2f) / (height/2f);
                float distanceFromCenter = (float)Math.sqrt(centerDistX * centerDistX + centerDistY * centerDistY);

                // Create base color with edge lighting
                Color currentColor = panelBaseColor.cpy().lerp(panelEdgeColor, centerDistX * 0.3f);

                // Add subtle top highlight
                if (y < height * 0.1f) {
                    float highlightIntensity = 1 - (y / (height * 0.1f));
                    currentColor.lerp(highlightColor, highlightIntensity * 0.15f);
                }

                // Add subtle bottom shadow
                if (y > height * 0.9f) {
                    float shadowIntensity = (y - height * 0.9f) / (height * 0.1f);
                    currentColor.lerp(shadowColor, shadowIntensity * 0.15f);
                }

                // Add subtle edge darkening
                float edgeDarkening = Math.max(centerDistX, centerDistY);
                currentColor.mul(1 - edgeDarkening * 0.15f);

                pixmap.setColor(currentColor);
                pixmap.drawPixel(x, y);
            }
        }

        // Add a subtle border
        int borderSize = 2;
        Color borderColor = new Color(1, 1, 1, 0.3f);
        pixmap.setColor(borderColor);

        // Draw border lines
        for (int i = 0; i < borderSize; i++) {
            // Top and bottom borders
            for (int x = 0; x < width; x++) {
                pixmap.drawPixel(x, i);                    // Top
                pixmap.drawPixel(x, height - 1 - i);      // Bottom
            }
            // Left and right borders
            for (int y = 0; y < height; y++) {
                pixmap.drawPixel(i, y);                    // Left
                pixmap.drawPixel(width - 1 - i, y);       // Right
            }
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
