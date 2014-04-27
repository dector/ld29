package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.List;

public class Level0 extends Level {

    private final int[] fishColors = new int[] {
            0xff0000, 0x00ff00, 0x0000ff
    };

    @Override
    public ShotResult makePhoto(Pointer cam, List<FlxObject> objects) {
        ShotResult result = new ShotResult();

        for (int i = 0; i < objects.size() && result.getType() != ShotResultType.LEVEL_FINISHED; i++) {
            FlxObject object = objects.get(i);

            if (object instanceof Fish) {
                Fish fish = (Fish) object;

                boolean fullInPointer = isObjectFullInPointer(cam, fish);

                if (fullInPointer) {
                    result.setType(ShotResultType.LEVEL_FINISHED);
                } else if (! result.hasMessage()) {
                    result.setMessage("Fish isn't fully visible in camera");
                }
            }
        }

        if (objects.isEmpty()) {
            result.setMessage("No any fish in camera viewfinder");
        }

        if (result.getType() == ShotResultType.LEVEL_FINISHED) {
            result.setMessage(null);
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
    public String getFailText() {
        return "No-no. Make photo of whole fish";
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return Level1.class;
    }
}
