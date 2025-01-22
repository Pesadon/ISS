package io.iss.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import java.util.HashSet;

public class JournalManager {
    private static JournalManager instance;
    private String content;
    private HashSet<String> addedIds;

    private static final String PREFERENCES_NAME = "JournalManagerPreferences";
    private static final String JSON_KEY = "JournalManagerData";

    private JournalManager() {
        content = "";
        addedIds = new HashSet<>();
    }

    public static synchronized JournalManager getInstance() {
        if (instance == null) {
            instance = new JournalManager();
        }
        return instance;
    }

    public String getContent() {
        return content;
    }

    public void prependTextWithId(String id, String text) {
        if (addedIds.contains(id)) {
            return;
        }

        addedIds.add(id);
        content = text + (content.isEmpty() ? "" : "\n" + content);
    }

    public void appendTextWithId(String id, String text) {
        if (addedIds.contains(id)) {
            return;
        }

        addedIds.add(id);
        content = content + (content.isEmpty() ? "" : "\n") + text;
    }

    public void clean(){
        content = "";
        addedIds.clear();
    }

    public void save() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        Json json = new Json();
        String jsonData = json.toJson(this);
        System.out.println("Salvataggio JSON: " + jsonData);
        prefs.putString(JSON_KEY, jsonData);
        prefs.flush();
    }

    public static void load() {
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_NAME);
        String jsonString = prefs.getString(JSON_KEY, null);
        System.out.println("Caricamento JSON: " + jsonString);

        if (jsonString != null) {
            Json json = new Json();
            instance = json.fromJson(JournalManager.class, jsonString);
        } else {
            instance = new JournalManager();
            System.err.println("Nessun JSON salvato trovato.");
        }
    }
}
