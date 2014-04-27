package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.ArrayList;
import java.util.List;

public class LevelLast extends Level {

    @Override
    public ShotResult makePhoto(Pointer cam, List<FlxObject> objects) {
        ShotResult shotResult = new ShotResult();
        shotResult.type = ShotResultType.LEVEL_FINISHED;
        return shotResult;
    }

    @Override
    public int newColor() {
        return MathUtils.random(0x00, 0xff) << 16
                | MathUtils.random(0x00, 0xff) << 8
                | MathUtils.random(0x00, 0xff);
    }

    @Override
    public int getMaxFishCount() {
        return 40;
    }

    @Override
    public int getMaxPlantsCount() {
        return 20;
    }

    @Override
    public String getGoalText() {
        return "Relax. <F> for fullscreen";
    }

    @Override
    public String getFailText() {
        return "";
    }

    @Override
    public Class<? extends Level> getNextLevel() {
        return null;
    }
}
