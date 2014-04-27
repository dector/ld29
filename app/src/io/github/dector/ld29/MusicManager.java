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
        setMuted(! muted);
    }

    private void setMuted(boolean muted) {
        this.muted = muted;

        if (muted) {
            pause();
        } else {
            play();
        }
    }

    public boolean isMuted() {
        return muted;
    }

    public boolean volumeUp() {
        setMuted(false);

        float volume = music.getVolume();

        volume += .1f;
        if (volume > 1) {
            volume = 1;
        }

        music.setVolume(volume);

        return volume != 1;
    }

    public boolean volumeDown() {
        float volume = music.getVolume();

        volume -= .1f;
        if (volume < 0) {
            volume = 0;
        }

        if (volume == 0) {
            setMuted(true);
        }

        music.setVolume(volume);

        return volume != 0;
    }
}
