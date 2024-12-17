package io.iss.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GameAssetManager {
    private static GameAssetManager instance;
    private final AssetManager assetManager;

    public static final String MENU_BG_TEXTURE = "images/menu.jpg";
    public static final String DEAD_DETECTIVE_BG = "backgrounds/dead_detective.png";
    public static final String NO_DEAD_DETECTIVE_BG = "backgrounds/no_dead_detective.png";
    public static final String DOOR_TEXTURE = "images/door.png";
    public static final String DANTE_TEXTURE = "images/Dante.jpg";
    public static final String SISTER_IDLE_TEXTURE = "characters/player/playerIdle.png";
    public static final String DETECTIVE_IDLE_TEXTURE = "characters/player/nomeIdle.png";
    public static final String OFFICE_TEXTURE = "backgrounds/office.jpg";
    public static final String DIALOGUES_JSON = "helldialog.json";

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
        assetManager.load(SISTER_IDLE_TEXTURE, Texture.class);
        assetManager.load(DETECTIVE_IDLE_TEXTURE, Texture.class);
        assetManager.load(OFFICE_TEXTURE, Texture.class);
        assetManager.load(NO_DEAD_DETECTIVE_BG, Texture.class);
        assetManager.load(DEAD_DETECTIVE_BG, Texture.class);

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
