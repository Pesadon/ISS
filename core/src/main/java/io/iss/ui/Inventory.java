package io.iss.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import io.iss.objects.GameObject;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class Inventory {
    private static final String PREFERENCES_NAME = "InventoryPreferences";
    private static final String JSON_KEY = "InventoryData";

    private static Inventory instance;
    private final Array<GameObject> items = new Array<>();
    private final Table inventoryBar;
    private GameObject selectedItem = null;
    private final HashSet<String> collectedItems = new HashSet<>();

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
        assertNotNull(item);
        if (collectedItems.contains(item.getId())) {
            return;
        }

        collectedItems.add(item.getId());
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

    public void removeItem(String key){
        for (int i = 0; i < items.size; i++) {
            if (items.get(i).getId().equals(key)  ){
                inventoryBar.getChild(i).remove();
                items.removeIndex(i);
            }
        }
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

    public boolean isCollected(String itemId) {
        return collectedItems.contains(itemId);
    }

    public void clear() {
        items.clear();
        inventoryBar.clear();
        collectedItems.clear();
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


    public void save() {
        Json json = new Json();

        InventoryData inventoryData = new InventoryData();
        inventoryData.items = new ArrayList<>();
        for (GameObject item : items) {
            inventoryData.items.add(item.getId());
        }
        inventoryData.collectedItems = new ArrayList<>(collectedItems);

        String jsonData = json.toJson(inventoryData);

        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        preferences.putString(JSON_KEY, jsonData);
        preferences.flush();
    }

    public void load() {
        Preferences preferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        String jsonData = preferences.getString(JSON_KEY, null);

        if (jsonData != null) {
            Json json = new Json();

            InventoryData inventoryData = json.fromJson(InventoryData.class, jsonData);

            items.clear();
            for (String itemId : inventoryData.items) {
                Texture texture = getTextureById(itemId);
                GameObject object = new GameObject(itemId, texture);
                object.setVisible(true);
                items.add(object);

                Image itemImage = new Image(new TextureRegionDrawable(new TextureRegion(object.getTexture())));

                itemImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        selectItem(object);
                        removeGuiSelection();
                        itemImage.setColor(Color.YELLOW);
                    }
                });

                inventoryBar.add(itemImage).size(80f, 80f).pad(20);
            }

            collectedItems.clear();
            collectedItems.addAll(inventoryData.collectedItems);
        }
    }

    private Texture getTextureById(String id) {
        return new Texture("images/" + id + ".png");
    }

    private static class InventoryData {
        public ArrayList<String> items;
        public ArrayList<String> collectedItems;
    }
}
