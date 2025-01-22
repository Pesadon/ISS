package io.iss.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

public class InteractiveArea {
    private Polygon polygon;
    private boolean isHovered;
    private Runnable onClick;

    public InteractiveArea(float[] vertices, Runnable onClick) {
        this.polygon = new Polygon(vertices);
        this.onClick = onClick;
        this.isHovered = false;
    }

    // Verifica se un punto è all'interno del poligono
    public boolean contains(float x, float y) {
        return polygon.contains(x, y);
    }

    public void checkClick(float x, float y) {
        if (polygon.contains(x, y) && Gdx.input.justTouched()) {
            onClick.run();
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        // Disegna un contorno quando il mouse è sopra
        if (isHovered) {
            shapeRenderer.setColor(1, 1, 1, 0.5f);
            shapeRenderer.polygon(polygon.getTransformedVertices());
        }
    }

    public void setHovered(boolean hovered) {
        this.isHovered = hovered;
    }
}
