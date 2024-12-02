package io.iss.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ObjectMap;

public class Character extends Actor {

    private String name;
    private ObjectMap<CharacterState, TextureRegion> sprites;
    private CharacterState currentState;

    public Character(String name, float x, float y) {
        this.name = name;
        this.sprites = new ObjectMap<>();
        this.currentState = CharacterState.IDLE;
        setPosition(x, y);
    }

    public void addSprite(CharacterState state, TextureRegion sprite) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        sprites.put(state, sprite);
        setSize(sprite.getRegionWidth(), sprite.getRegionHeight());
    }

    public void setState(CharacterState state) {
        if (sprites.containsKey(state)) {
            currentState = state;
        } else {
            throw new IllegalArgumentException("State not found: " + state);
        }
    }

    public CharacterState getCurrentState() {
        return currentState;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion sprite = sprites.get(currentState);
        if (sprite != null) {
            batch.draw(sprite, getX(), getY(), getWidth(), getHeight());
        }
    }
}
