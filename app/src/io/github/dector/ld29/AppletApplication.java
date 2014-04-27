package io.github.dector.ld29;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplet;
import org.flixel.FlxGame;

public class AppletApplication extends LwjglApplet {

    public AppletApplication(FlxGame game) {
        super((ApplicationListener) game.stage, false);
    }
}
