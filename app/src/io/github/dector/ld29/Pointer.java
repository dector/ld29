package io.github.dector.ld29;

import org.flixel.FlxG;
import org.flixel.FlxSprite;

public class Pointer extends FlxSprite {

    public Pointer() {
        loadGraphic("assets/camera.png", false, false, 100, 80);
    }

    @Override
    public void update() {
        super.update();

        x = FlxG.mouse.screenX - width / 2;
        y = FlxG.mouse.screenY - height / 2;
    }

    public void makePhoto(Level.ShotResult shotResult) {
        if (shotResult.type == Level.ShotResultType.WRONG) {
            FlxG.flash(0x88eeeeee, .5f);
        } else {
            FlxG.flash(0x88ffffff, .5f);
        }
    }
}
