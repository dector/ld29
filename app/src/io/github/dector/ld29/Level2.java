package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.List;

public class Level2 extends Level {

    private static final int BIG_FISH_COUNT_GOAL = 3;

    private final int[] fishColors = new int[] {
            0xff0000, 0x00ff00, 0x0000ff, 0xffff00, 0x00ffff
    };

    private int photosCount;

    @Override
    public ShotResult makePhoto(Pointer cam, List<FlxObject> objects) {
        ShotResult result = new ShotResult();

        boolean aimedOnBigFish = false;

        for (int i = 0; i < objects.size() && result.getType() != ShotResultType.LEVEL_FINISHED; i++) {
            FlxObject object = objects.get(i);

            if (object instanceof Fish) {
                Fish fish = (Fish) object;

                boolean fullInPointer = isObjectFullInPointer(cam, fish);
                boolean bigEnough = fish.getSize().scaleFactor >= Fish.Size.BIG.scaleFactor;
                aimedOnBigFish |= bigEnough;

                if (fullInPointer && bigEnough && ! fish.hasPhoto) {
                    fish.hasPhoto = true;

                    photosCount++;
                    if (photosCount == BIG_FISH_COUNT_GOAL) {
                        result.setType(ShotResultType.LEVEL_FINISHED);
                    } else {
                        result.setType(ShotResultType.CORRECT);
                    }
                } else if (! result.hasMessage()) {
                    if (fish.hasPhoto) {
                        result.setMessage("We already have photo of this fish");
                    } else {
                        result.setMessage("Fish isn't fully visible in camera");
                    }
                }
            }
        }

        if (objects.isEmpty()) {
            result.setMessage("No any fish in camera viewfinder");
        } else if (! aimedOnBigFish) {
            result.setMessage("Fish isn't big enough");
        }

        if (result.getType() == ShotResultType.CORRECT) {
            result.setMessage(null);
        } else if (result.getType() == ShotResultType.LEVEL_FINISHED) {
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
    public Fish.Size getSize() {
        Fish.Size size = super.getSize();
        if (size.scaleFactor >= Fish.Size.BIG.scaleFactor && MathUtils.random() > .1f) {
            size = super.getSize();
        }

        return size;
    }

    @Override
    public int getMaxFishCount() {
        return 20;
    }

    @Override
    public String getGoalText() {
        if (photosCount == 0) {
            return "We need 3 photos of big fish";
        } else {
            return "Only " + (BIG_FISH_COUNT_GOAL - photosCount) + " photos with big fish left";
        }
    }

    @Override
    public String getFailText() {
        return "Oops. Aim better";
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return null;
    }
}
