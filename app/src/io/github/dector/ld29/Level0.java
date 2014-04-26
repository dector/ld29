package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.List;

public class Level0 extends Level {

    private final int[] fishColors = new int[] {
            0xff0000, 0x00ff00, 0x0000ff
    };

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

    @Override
    public int newColor() {
        return fishColors[MathUtils.random(fishColors.length - 1)];
    }

    @Override
    public int getMaxFishCount() {
        return 10;
    }

    @Override
    public String getGoalText() {
        return "Try to take picture of some fish";
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return null;
    }
}
