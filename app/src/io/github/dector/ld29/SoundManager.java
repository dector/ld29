package io.github.dector.ld29;

import org.flixel.FlxG;
import org.flixel.FlxSound;

public class SoundManager {

    private boolean muted = true;

    private FlxSound shotSound;
    private FlxSound shotWrongSound;

    public SoundManager() {
        shotSound = FlxG.loadSound("assets/shot.wav");
        shotWrongSound = FlxG.loadSound("assets/shotWrong.wav");
    }

    public void playWrong() {
        if (muted) return;

        shotWrongSound.play(true);
    }

    public void playShot() {
        if (muted) return;

        shotSound.play(true);
    }
}
