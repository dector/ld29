package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.List;

public class Level1 extends Level {

    private final int[] fishColors = new int[] {
            0xff0000, 0x00ff00, 0x0000ff, 0xffff00, 0x00ffff
    };

    @Override
    public ShotResult makePhoto(Pointer cam, List<FlxObject> objects) {
        ShotResult result = new ShotResult();

        boolean aimedOnRedFish = false;

        for (int i = 0; i < objects.size() && result.getType() != ShotResultType.LEVEL_FINISHED; i++) {
            FlxObject object = objects.get(i);

            if (object instanceof Fish) {
                Fish fish = (Fish) object;

                boolean fullInPointer = isObjectFullInPointer(cam, fish);
                boolean redFish = (fish.getColor() & 0xffffff) == 0xff0000;
                aimedOnRedFish |= redFish;

                if (fullInPointer && redFish) {
                    result.setType(ShotResultType.LEVEL_FINISHED);
                } else if (! result.hasMessage()) {
                    result.setMessage("Fish isn't fully visible in camera");
                }
            }
        }

        if (objects.isEmpty()) {
            result.setMessage("No any fish in camera viewfinder");
        } else if (! aimedOnRedFish) {
            result.setMessage("We are looking for red fish");
        }

        if (result.getType() == ShotResultType.LEVEL_FINISHED) {
            result.setMessage(null);
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
        return Level2.class;
    }
}
