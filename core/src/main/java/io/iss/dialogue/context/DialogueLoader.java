package io.iss.dialogue.context;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import io.iss.dialogue.model.DialogueChoice;
import io.iss.dialogue.model.DialogueEntry;
import io.iss.dialogue.model.DialogueScene;

public class DialogueLoader {
    private Json json;
    private ObjectMap<String, DialogueScene> loadedScenes;

    public DialogueLoader() {
        json = new Json();
        loadedScenes = new ObjectMap<>();
        setupJson();
    }

    private void setupJson() {
        // Configure Json serializer to handle our custom classes
        json.setSerializer(DialogueEntry.class, new Json.Serializer<DialogueEntry>() {
            @Override
            public void write(Json json, DialogueEntry entry, Class knownType) {
                // Implementation for writing if needed
                json.writeObjectStart();
                json.writeValue("character", entry.getCharacter());
                json.writeValue("text", entry.getText());
                if (entry.hasChoices()) {
                    json.writeArrayStart("choices");
                    for (DialogueChoice choice : entry.getChoices()) {
                        json.writeObjectStart();
                        json.writeValue("text", choice.getText());
                        json.writeValue("next_scene", choice.getNextScene());
                        json.writeObjectEnd();
                    }
                    json.writeArrayEnd();
                }
                json.writeObjectEnd();
            }

            @Override
            public DialogueEntry read(Json json, JsonValue jsonData, Class type) {
                DialogueEntry entry = new DialogueEntry();
                entry.setCharacter(jsonData.getString("character"));
                entry.setText(jsonData.getString("text", ""));

                // Parse choices if present
                JsonValue choicesJson = jsonData.get("choices");
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
        });

        // Add serializer for DialogueScene if needed
        json.setSerializer(DialogueScene.class, new Json.Serializer<DialogueScene>() {
            @Override
            public void write(Json json, DialogueScene scene, Class knownType) {
                json.writeObjectStart();
                json.writeValue("map", scene.getMapPath());
                json.writeArrayStart("dialogs");
                for (DialogueEntry entry : scene.getDialogues()) {
                    json.writeValue(entry);
                }
                json.writeArrayEnd();
                json.writeObjectEnd();
            }

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

    public void loadDialogues(String filePath) {
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

                entries.add(entry);
            }

            loadedScenes.put(sceneId, new DialogueScene(mapPath, entries));
        }
    }

    public DialogueScene getScene(String sceneId) {
        return loadedScenes.get(sceneId);
    }
}
