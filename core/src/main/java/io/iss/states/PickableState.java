package io.iss.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import io.iss.screens.GameScreen;
import io.iss.utils.GameAssetManager;
import io.iss.utils.InventoryController;

public class PickableState extends GameState {

    private final Array<Image> inventorySlots = new Array<>();
    private final Group inventoryGroup = new Group(); // Gruppo per l'inventario
    private Image selectedInventoryItem = null;
    InventoryController controller;

    public PickableState(GameScreen screen) {
        super(screen);
    }

    @Override
    public void enter() {
        Image backgroundImage = new Image(GameAssetManager.getInstance().get(GameAssetManager.OFFICE_TEXTURE, Texture.class));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        // Configura il layout dell'inventario
        createInventory();
        controller = new InventoryController(this);

        // Aggiungi oggetti nello scenario
        for (int i = 0; i < 3; i++) {
            final Image item = new Image(GameAssetManager.getInstance().get(GameAssetManager.KEY_TEXTURE, Texture.class));
            item.setSize(128, 128);
            item.setPosition(150 + i * 100, 250); // Posizione iniziale
            stage.addActor(item);

            // Listener per gli oggetti nello scenario
            item.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.onItemClicked(item);
                }
            });
        }

        // Listener per i singoli slot dell'inventario
        for (final Image slot : inventorySlots) {
            slot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.onInventorySlotClicked(slot);
                }
            });
        }

        // Set up input processing
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void exit() {
        stage.clear();
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }

    private void createInventory() {
        float inventoryWidth = 300;
        float inventoryHeight = 128;
        float slotSize = 128;
        float padding = 5;

        // Sfondo dell'inventario
        Image background = new Image();
        background.setSize(inventoryWidth, inventoryHeight);
        background.setColor(64, 64, 64, 1f); // Semitrasparente
        inventoryGroup.addActor(background);

        // Creazione degli slot
        for (int i = 0; i < 5; i++) {
            Image slot = new Image(GameAssetManager.getInstance().get(GameAssetManager.SLOT_TEXTURE, Texture.class));
            slot.setSize(slotSize, slotSize);
            slot.setColor(Color.WHITE); // Colore di default
            slot.setPosition(i * (slotSize + padding), 0);
            inventorySlots.add(slot);
            inventoryGroup.addActor(slot);
        }

        // Posizionamento dell'inventario in alto al centro
        inventoryGroup.setPosition((Gdx.graphics.getWidth() - inventoryWidth) / 2, Gdx.graphics.getHeight() - inventoryHeight - 10);
        stage.addActor(inventoryGroup);
    }

    // Metodo per aggiungere un oggetto all'inventario
    public void addToInventory(Image item) {
        for (Image slot : inventorySlots) {
            if (slot.getDrawable() == null) {
                slot.setDrawable(item.getDrawable());
                return;
            }
        }
    }

    // Metodo per selezionare un oggetto nell'inventario
    public void selectInventoryItem(Image slot) {
        if (selectedInventoryItem != null) {
            selectedInventoryItem.setColor(Color.LIGHT_GRAY); // Deseleziona
        }
        if (slot != selectedInventoryItem) {
            selectedInventoryItem = slot;
            selectedInventoryItem.setColor(Color.YELLOW); // Evidenzia
        } else {
            selectedInventoryItem = null;
        }
    }

    // Getter per l'oggetto selezionato
    public Image getSelectedInventoryItem() {
        return selectedInventoryItem;
    }
}
