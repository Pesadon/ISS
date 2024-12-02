package io.iss.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GameAssetManager {
    private static GameAssetManager instance;
    private final AssetManager assetManager;

    public static final String MENU_BG_TEXTURE = "images/menu.jpg";
    public static final String DOOR_TEXTURE = "images/door.png";
    public static final String DANTE_TEXTURE = "images/Dante.jpg";

    private GameAssetManager() {
        assetManager = new AssetManager();
    }

    public static GameAssetManager getInstance() {
        if (instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }

    public void loadInitialAssets() {
        assetManager.load(MENU_BG_TEXTURE, Texture.class);
        assetManager.load(DOOR_TEXTURE, Texture.class);
        assetManager.load(DANTE_TEXTURE, Texture.class);

        assetManager.finishLoading();
    }

    public <T> T get(String fileName, Class<T> type) {
        return assetManager.get(fileName, type);
    }

    public void dispose() {
        assetManager.dispose();
    }

    public boolean update() {
        return assetManager.update();
    }

    public float getProgress() {
        return assetManager.getProgress();
    }
}
