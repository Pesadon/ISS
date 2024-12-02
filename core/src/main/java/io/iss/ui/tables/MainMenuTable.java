package io.iss.ui.tables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.iss.commands.ContinueGameCommand;
import io.iss.commands.ExitCommand;
import io.iss.commands.MenuCommand;
import io.iss.commands.NewGameCommand;
import io.iss.screens.GameScreen;
import io.iss.ui.generators.ButtonAtlasGenerator;
import io.iss.ui.generators.PanelGenerator;
import io.iss.utils.FontManager;

public class MainMenuTable extends Table {
    private static final float BUTTON_PADDING = 20f;
    private static final float ANIMATION_DURATION = 0.3f;

    private final TextureAtlas buttonAtlas;
    private final Texture panelTexture;

    public MainMenuTable(GameScreen screen) {
        super(screen.getGame().getSkin());

        int panelWidth = 800;
        int panelHeight = 600;

        panelTexture = PanelGenerator.createMenuPanel(panelWidth, panelHeight);

        // This is important - it will take up the full screen but won't stretch our panel
        Table containerTable = new Table();
        containerTable.setFillParent(true);

        // Create our actual menu panel table that will hold our content
        Table menuPanel = new Table();
        menuPanel.setBackground(new TextureRegionDrawable(new TextureRegion(panelTexture)));

        buttonAtlas = ButtonAtlasGenerator.createButtonAtlas();

        // Add title
        Label titleLabel = new Label("[NAME OF THE GAME]", FontManager.getInstance().createTitleStyle());
        titleLabel.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.fadeIn(ANIMATION_DURATION)
        ));
        menuPanel.add(titleLabel).padTop(40).row();

        // Create fancy buttons
        createAnimatedButton(menuPanel, "New Game", new NewGameCommand(screen));
        createAnimatedButton(menuPanel, "Continue", new ContinueGameCommand(screen));
        createAnimatedButton(menuPanel, "Exit", new ExitCommand());

        containerTable.add(menuPanel)
            .size(panelWidth, panelHeight)
            .center()
            .expand();

        add(containerTable).grow();
    }

    private void createAnimatedButton(Table table, String text, final MenuCommand command) {
        TextButton button = new TextButton(text, getButtonStyle());

        // Add hover effect
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.addAction(Actions.sequence(
                    Actions.scaleTo(1.1f, 1.1f, 0.1f)
                    // Actions.color(Color.YELLOW, 0.2f)
                ));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.addAction(Actions.sequence(
                    Actions.scaleTo(1f, 1f, 0.1f),
                    Actions.color(Color.WHITE, 0.2f)
                ));
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Add click animation
                button.addAction(Actions.sequence(
                    Actions.scaleTo(0.9f, 0.9f, 0.1f),
                    Actions.scaleTo(1f, 1f, 0.1f),
                    Actions.run(command::execute)
                ));
            }
        });

        // Add button with animation
        button.setTransform(true);
        button.setOrigin(Align.center);
        button.addAction(Actions.sequence(
            Actions.alpha(0),
            Actions.parallel(
                Actions.fadeIn(ANIMATION_DURATION),
                Actions.moveBy(0, -20, ANIMATION_DURATION, Interpolation.swingOut)
            )
        ));

        table.add(button).pad(BUTTON_PADDING).width(200).row();
    }

    private TextButton.TextButtonStyle getButtonStyle() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = new TextureRegionDrawable(buttonAtlas.findRegion("button-up"));
        style.down = new TextureRegionDrawable(buttonAtlas.findRegion("button-down"));
        style.over = new TextureRegionDrawable(buttonAtlas.findRegion("button-hover"));

        style.font = FontManager.getInstance().getMenuFont();

        return style;
    }

    public void dispose() {
        panelTexture.dispose();
    }
}
