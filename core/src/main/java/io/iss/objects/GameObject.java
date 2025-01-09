package io.iss.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.iss.ui.Inventory;

public class GameObject extends Actor {
    private final Texture texture;
    private final String id;

    public GameObject(String id, Texture texture) {
        this.id = id;
        this.texture = texture;
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Inventory.getInstance().addItem(GameObject.this);
                remove();
            }
        });
    }

    public GameObject(String id, Texture texture, CustomPickObjectOperation operation) {
        this.id = id;
        this.texture = texture;
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                remove(); // Rimuove l'oggetto dalla scena.
                operation.execute();
                Inventory.getInstance().addItem(GameObject.this);
            }
        });
    }

    public String getId() {
        return id;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Texture getTexture() {
        return texture;
    }
}
