package io.github.dector.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import org.flixel.FlxG;
import org.flixel.FlxState;

public abstract class BaseState extends FlxState {

    @Override
    public void update() {
        super.update();

        if (FlxG.keys.justPressed("F")) {
            toggleFullscreen();
        }
    }

    private void toggleFullscreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setDisplayMode(800, 600, false);
        } else {
            Graphics.DisplayMode[] modes = Gdx.graphics.getDisplayModes();

            Graphics.DisplayMode selectedMode = null;
            if (modes.length > 0) {
                selectedMode = modes[modes.length - 1];
            }

            for (Graphics.DisplayMode mode : modes) {
                if (mode.width == 800 && mode.height == 600) {
                    selectedMode = mode;
                    break;
                }
            }

            if (selectedMode != null) {
                Gdx.graphics.setDisplayMode(selectedMode.width, selectedMode.height, true);
            }
        }
    }
}
