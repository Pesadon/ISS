package io.iss.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import io.iss.objects.GameObject;

public class Inventory {
    private static Inventory instance;
    private final Array<GameObject> items = new Array<>();
    private final Table inventoryBar;
    private GameObject selectedItem = null;

    private Inventory() {
        inventoryBar = new Table();
        inventoryBar.top();
        inventoryBar.setFillParent(false);
        inventoryBar.setPosition((float) Gdx.graphics.getWidth() /2, Gdx.graphics.getHeight());
    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    public void init(){
        clear();
        show();
    }


    public void addItem(GameObject item) {
        items.add(item);

        Image itemImage = new Image(new TextureRegionDrawable(new TextureRegion(item.getTexture())));

        itemImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectItem(item);
                removeGuiSelection();
                itemImage.setColor(Color.YELLOW);
            }
        });

        inventoryBar.add(itemImage).size(80f, 80f).pad(20);
        System.out.println("Added object: " + item.getId());
    }

    public void selectItem(GameObject item) {
        if (selectedItem != null) {
            deselectItem();
        }

        selectedItem = item;
    }

    public void deselectItem() {
        if (selectedItem != null) {
            selectedItem = null;
        }

    }

    public void removeGuiSelection(){
        for (int i = 0; i < inventoryBar.getChildren().size; i++) {
            if (inventoryBar.getChild(i) instanceof Image ){
                inventoryBar.getChild(i).setColor(Color.WHITE);
            }
        }
    }

    public GameObject getSelectedItem() {
        return selectedItem;
    }

    public void clear() {
        items.clear();
        inventoryBar.clear();
    }

    public void hide() {
        inventoryBar.setVisible(false);
        inventoryBar.setTouchable(Touchable.disabled);
    }

    public void show() {
        inventoryBar.setVisible(true);
        inventoryBar.setTouchable(Touchable.enabled);
    }

    public Table getInventoryBar() {
        return inventoryBar;
    }
}
