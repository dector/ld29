package io.github.dector.ld29;

import org.flixel.FlxObject;

import java.util.List;

public class Level0 extends Level {

    {
        maxFishCount = 10;

        fishColors = new int[] {
                0xff0000, 0x00ff00, 0x0000ff
        };

        goalText = "Try to take picture of some fish";
    }

    @Override
    public boolean makePhoto(Pointer cam, List<FlxObject> objects) {
        boolean result = false;

        for (int i = 0; i < objects.size() && ! result; i++) {
            FlxObject object = objects.get(i);

            if (object instanceof Fish) {
                Fish fish = (Fish) object;

                if (cam.x <= fish.x && fish.x + fish.width <= cam.x + cam.width
                        && cam.y <= fish.y && fish.y + fish.height <= cam.y + cam.height) {
                    result = true;
                }
            }
        }

        return result;
    }
}
