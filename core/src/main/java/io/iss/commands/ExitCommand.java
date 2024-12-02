package io.iss.commands;

import com.badlogic.gdx.Gdx;

public class ExitCommand implements MenuCommand{
    @Override
    public void execute() {
        Gdx.app.exit();
    }
}
