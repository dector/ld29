package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.ArrayList;
import java.util.List;

public class Level3 extends Level {

    private static final int PHOTOS_GOAL = 3;

    private int photosCount;

    private boolean checkResult;
    private List<Fish> bigFishes = new ArrayList<Fish>();
    private List<Fish> smallFishes = new ArrayList<Fish>();

    @Override
    public ShotResult makePhoto(Pointer cam, List<FlxObject> objects) {
        ShotResult result = new ShotResult();

        boolean aimedOnBigFish = false;

        for (int i = 0; i < objects.size(); i++) {
            FlxObject object = objects.get(i);

            if (object instanceof Fish) {
                Fish fish = (Fish) object;

                checkResult |= true;

                boolean fullInPointer = isObjectFullInPointer(cam, fish);
                boolean bigEnough = fish.getSize().scaleFactor >= Fish.Size.BIG.scaleFactor;
                aimedOnBigFish |= bigEnough;

                if (fullInPointer && ! fish.hasPhoto) {
                    if (bigEnough) {
                        bigFishes.add(fish);
                    } else {
                        smallFishes.add(fish);
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

        if (checkResult) {
            if (bigFishes.isEmpty()) {
                result.setMessage("Can't see big fish here, huh");
            } else if (smallFishes.isEmpty()) {
                result.setMessage("Try to catch smaller one with bigger fish");
            } else {

                for (Fish f : bigFishes) {
                    f.hasPhoto = true;
                }
                for (Fish f : smallFishes) {
                    f.hasPhoto = true;
                }

                bigFishes.clear();
                smallFishes.clear();

                photosCount++;
                if (photosCount == PHOTOS_GOAL) {
                    result.setType(ShotResultType.LEVEL_FINISHED);
                } else {
                    result.setType(ShotResultType.CORRECT);
                }
            }

            checkResult = false;
        }

        if (objects.isEmpty()) {
            result.setMessage("No any fish in camera viewfinder");
        } else if (objects.size() == 1) {
            result.setMessage("We need two fishes on one photo");
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
        return MathUtils.random(0x00, 0xff) << 16
                | MathUtils.random(0x00, 0xff) << 8
                | MathUtils.random(0x00, 0xff);
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
        return 30;
    }

    @Override
    public int getMaxPlantsCount() {
        return 15;
    }

    @Override
    public String getGoalText() {
        if (photosCount == 0) {
            return "Now make two photos of big fish with smaller one";
        } else {
            return "Only " + (PHOTOS_GOAL - photosCount) + " photos left";
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
