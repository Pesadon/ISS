package io.iss.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.iss.ui.Inventory;

public class InteractiveObject extends Actor {
    private final Texture texture;
    private final String interactableObjectId;


    public InteractiveObject(Texture texture, String interactableObjectId, CustomClickInteractiveObjectOperation operation, ObjectInteraction interaction) {
        this.texture = texture;
        this.interactableObjectId = interactableObjectId;
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameObject selectedItem = Inventory.getInstance().getSelectedItem();
                if (selectedItem != null) {
                    interact(selectedItem, operation, interaction);
                } else {
                    operation.execute();
                }
            }
        });
    }

    private void interact(GameObject item, CustomClickInteractiveObjectOperation operation, ObjectInteraction interaction) {
        if (item.getId().equals(interactableObjectId)) {
            interaction.execute();
            Inventory.getInstance().deselectItem();
        } else {
            //For now lets just do what it would happen if the object was clicked without a selected item
            //Eventually we could even have a standard reaction when using the wrong GameObject with the wrong InteractiveObject
            operation.execute();
            Inventory.getInstance().deselectItem();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
