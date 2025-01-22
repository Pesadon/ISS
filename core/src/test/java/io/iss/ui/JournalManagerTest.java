package io.iss.ui;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class JournalManagerTest {

    private JournalManager journalManager;

    @Before
    public void setUp() {
        journalManager = JournalManager.getInstance();

        journalManager.setContent("");
        journalManager.setAddedIds(new HashSet<>());
    }

    @Test
    public void testSingletonInstance() {
        JournalManager instance1 = JournalManager.getInstance();
        JournalManager instance2 = JournalManager.getInstance();
        assertSame("JournalManager is not a singleton!", instance1, instance2);
    }

    @Test
    public void testDuplicateIds() {
        boolean firstAdd = journalManager.appendTextWithId("id1", "First line");
        assertFalse("First string should be added correctly!", firstAdd);

        boolean secondAdd = journalManager.appendTextWithId("id1", "Duplicate");
        assertTrue("It shouldn't be possible to add a second string with the same ID!", secondAdd);

        assertEquals("Content should not have been modified!", "First line", journalManager.getContent());
    }

    @Test
    public void prependTextWithId() {
        boolean added = journalManager.prependTextWithId("id1", "First line");
        assertFalse("The string should not be already there", added);

        assertEquals("Content does not match","First line", journalManager.getContent());

        boolean addedAgain = journalManager.prependTextWithId("id2", "New line");
        assertFalse("The string should not be already there", addedAgain);

        assertEquals("Content is not correct!", "New line\nFirst line", journalManager.getContent());
    }

    @Test
    public void appendTextWithId() {
        boolean added = journalManager.appendTextWithId("id1", "First line");
        assertFalse("The string shiuld not be already there", added);

        assertEquals("Content does not match","First line", journalManager.getContent());

        boolean addedAgain = journalManager.appendTextWithId("id2", "New line");
        assertFalse("It should't be possible to add a new line with the same ID", addedAgain);

        assertEquals("Content is not correct!", "First line\nNew line", journalManager.getContent());
    }
}
