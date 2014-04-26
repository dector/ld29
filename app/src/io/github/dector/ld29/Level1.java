package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.List;

public class Level1 extends Level {

    private final int[] fishColors = new int[] {
            0xff0000, 0x00ff00, 0x0000ff, 0xffff00, 0x00ffff
    };

    @Override
    public boolean makePhoto(Pointer cam, List<FlxObject> objects) {
        boolean result = false;

        for (int i = 0; i < objects.size() && ! result; i++) {
            FlxObject object = objects.get(i);

            if (object instanceof Fish) {
                Fish fish = (Fish) object;

                if (cam.x <= fish.x && fish.x + fish.width <= cam.x + cam.width
                        && cam.y <= fish.y && fish.y + fish.height <= cam.y + cam.height
                        && ((fish.getColor() & 0xff0000) == 0xff0000)) {
                    result = true;
                }
            }
        }

        return result;
    }

    @Override
    public int newColor() {
        int colorIndex = MathUtils.random(fishColors.length - 1);
        if (colorIndex == 0 && MathUtils.random() > .1f) {
            colorIndex = MathUtils.random(fishColors.length - 1);
        }
        return fishColors[colorIndex];
    }

    @Override
    public int getMaxFishCount() {
        return 20;
    }

    @Override
    public String getGoalText() {
        return "Now try to get photo of red fish";
    }

    @Override
    public String getFailText() {
        return "We need red fish";
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return null;
    }
}
