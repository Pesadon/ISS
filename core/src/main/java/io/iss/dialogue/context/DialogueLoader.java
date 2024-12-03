package io.iss.dialogue.context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import io.iss.dialogue.model.DialogueChoice;
import io.iss.dialogue.model.DialogueEntry;
import io.iss.dialogue.model.DialogueScene;

public class DialogueLoader {
    private final Json json;
    private final ObjectMap<String, DialogueScene> loadedScenes;

    public DialogueLoader(String filePath) {
        json = new Json();
        loadedScenes = new ObjectMap<>();

        setupJson();
        loadDialogues(filePath);
    }

    private void setupJson() {
        // Configure Json serializer to handle our custom classes
        json.setSerializer(DialogueEntry.class, new Json.Serializer<DialogueEntry>() {
            @Override
            public void write(Json json, DialogueEntry entry, Class knownType) { }

            @Override
            public DialogueEntry read(Json json, JsonValue jsonData, Class type) {
                return getDialogueEntry(jsonData);
            }
        });

        // Add serializer for DialogueScene if needed
        json.setSerializer(DialogueScene.class, new Json.Serializer<DialogueScene>() {
            @Override
            public void write(Json json, DialogueScene scene, Class knownType) { }

            @Override
            public DialogueScene read(Json json, JsonValue jsonData, Class type) {
                String mapPath = jsonData.getString("map");
                Array<DialogueEntry> entries = new Array<>();

                JsonValue dialogsArray = jsonData.get("dialogs");
                for (JsonValue dialogueJson : dialogsArray) {
                    entries.add(json.readValue(DialogueEntry.class, dialogueJson));
                }

                return new DialogueScene(mapPath, entries);
            }
        });
    }

    private void loadDialogues(String filePath) {
        FileHandle file = Gdx.files.internal(filePath);
        JsonValue root = new JsonReader().parse(file);

        JsonValue scenesJson = root.get("scenes");
        for (JsonValue sceneJson : scenesJson) {
            String sceneId = sceneJson.name;

            // Parse map path
            String mapPath = sceneJson.getString("map");

            // Parse dialogues
            Array<DialogueEntry> entries = new Array<>();
            JsonValue dialoguesJson = sceneJson.get("dialogs");

            for (JsonValue dialogueJson : dialoguesJson) {
                entries.add(getDialogueEntry(dialogueJson));
            }

            loadedScenes.put(sceneId, new DialogueScene(mapPath, entries));
        }
    }

    private DialogueEntry getDialogueEntry(JsonValue dialogueJson) {
        DialogueEntry entry = new DialogueEntry();
        entry.setCharacter(dialogueJson.getString("character"));
        entry.setText(dialogueJson.getString("text", ""));

        // Parse choices if present
        JsonValue choicesJson = dialogueJson.get("choices");
        if (choicesJson != null) {
            for (JsonValue choiceJson : choicesJson) {
                DialogueChoice choice = new DialogueChoice(
                    choiceJson.getString("text"),
                    choiceJson.getString("next_scene")
                );
                entry.getChoices().add(choice);
            }
        }

        return entry;
    }

    public DialogueScene getScene(String sceneId) {
        return loadedScenes.get(sceneId);
    }
}
