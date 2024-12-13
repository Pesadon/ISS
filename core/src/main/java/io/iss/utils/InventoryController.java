package io.iss.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.iss.states.PickableState;

public class InventoryController {
    private final PickableState view;

    public InventoryController(PickableState view) {
        this.view = view;
    }

    // Quando un oggetto nello scenario viene cliccato
    public void onItemClicked(Image item) {
        if (view.getSelectedInventoryItem() == null) {
            // Aggiungi l'oggetto all'inventario se non è selezionato nulla
            view.addToInventory(item);
            item.remove(); // Rimuovi l'oggetto dalla scena
        } else {
            // Usa l'oggetto selezionato sull'oggetto cliccato
            System.out.println("Usa " + view.getSelectedInventoryItem() + " su " + item);
        }
    }

    // Quando uno slot dell'inventario viene cliccato
    public void onInventorySlotClicked(Image slot) {
        view.selectInventoryItem(slot);
    }
}
