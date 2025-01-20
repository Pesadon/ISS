package io.iss.ui;

import java.util.HashSet;

public class JournalManager {
    private static JournalManager instance;
    private StringBuilder content;
    private HashSet<String> addedIds;

    private JournalManager() {
        content = new StringBuilder();
        addedIds = new HashSet<>();
    }

    public static synchronized JournalManager getInstance() {
        if (instance == null) {
            instance = new JournalManager();
        }
        return instance;
    }

    public String getContent() {
        return content.toString();
    }

    public void prependTextWithId(String id, String text) {
        if (addedIds.contains(id)) {
            return;
        }

        addedIds.add(id);
        if (!content.isEmpty()) {
            content.insert(0, text + "\n\n");
        } else {
            content.append(text);
        }
    }

    public void clean(){
        content.setLength(0);
        addedIds.clear();
    }

}
