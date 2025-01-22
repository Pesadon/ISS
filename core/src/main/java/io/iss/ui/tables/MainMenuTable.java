package io.iss.ui.tables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.iss.commands.ContinueGameCommand;
import io.iss.commands.ExitCommand;
import io.iss.commands.NewGameCommand;
import io.iss.screens.GameScreen;
import io.iss.ui.Buttons;
import io.iss.ui.generators.PanelGenerator;
import io.iss.utils.FontManager;

public class MainMenuTable extends Table {
    private static final float ANIMATION_DURATION = 0.3f;
    private Buttons buttons;
    private final Texture panelTexture;
    private boolean isPanelTextureDisposed = false; // Flag to track if disposed

    public MainMenuTable(GameScreen screen) {
        super(screen.getGame().getSkin());

        int panelWidth = 800;
        int panelHeight = 600;

        panelTexture = PanelGenerator.createMenuPanel(panelWidth, panelHeight);

        buttons = new Buttons();

        // This is important - it will take up the full screen but won't stretch our panel
        Table containerTable = new Table();
        containerTable.setFillParent(true);

        // Create our actual menu panel table that will hold our content
        Table menuPanel = new Table();
        menuPanel.setBackground(new TextureRegionDrawable(new TextureRegion(panelTexture)));

        // Add title
        Label titleLabel = new Label("Memento Paradox", FontManager.getInstance().createTitleStyle());
        titleLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(ANIMATION_DURATION)
        ));
        menuPanel.add(titleLabel).padTop(40).row();

        // Create fancy buttons
        buttons.createAnimatedButton(menuPanel, "New Game", new NewGameCommand(screen));
        buttons.createAnimatedButton(menuPanel, "Continue", new ContinueGameCommand(screen));
        buttons.createAnimatedButton(menuPanel, "Exit", new ExitCommand());

        containerTable.add(menuPanel)
            .size(panelWidth, panelHeight)
            .center()
            .expand();

        add(containerTable).grow();
    }

    public void setButtons(Buttons buttons) {
        this.buttons = buttons;
    }

    public Texture getPanelTexture() {
        return panelTexture;
    }


    public void dispose() {
        // Dispose resources manually
        buttons.dispose();
        panelTexture.dispose();
        isPanelTextureDisposed = true; // Set flag when disposed
    }

    public boolean isPanelTextureDisposed() {
        return isPanelTextureDisposed; // Return the flag status
    }
}

