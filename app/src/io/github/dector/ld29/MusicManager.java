package io.github.dector.ld29;

import org.flixel.FlxSound;

public class MusicManager {

    public static final MusicManager instance = new MusicManager();

    private boolean muted;

    private FlxSound music;

    private MusicManager() {
        music = new FlxSound().loadEmbedded("assets/music.mp3", true, false, FlxSound.MUSIC);
        music.setVolume(.7f);
    }

    public void play() {
        if (muted) return;

        music.play();
    }

    public void pause() {
        music.pause();
    }

    public void switchMute() {
        muted = ! muted;

        if (muted) {
            pause();
        } else {
            play();
        }
    }
}
