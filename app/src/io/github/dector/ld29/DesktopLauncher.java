package io.github.dector.ld29;

import org.flixel.FlxDesktopApplication;

public class DesktopLauncher {

    public static void main(String[] args) {
        new FlxDesktopApplication(new Game(), "Blue waves", 800, 600, false);
    }
}
