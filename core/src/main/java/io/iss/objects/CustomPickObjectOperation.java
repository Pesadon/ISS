package io.iss.objects;

/**
 *  Custom action to be executed when picking up a GameObject, before putting it into the inventory
 */
public interface CustomPickObjectOperation {
    void execute();
}
