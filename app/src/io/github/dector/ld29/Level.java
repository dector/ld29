package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.List;

public abstract class Level {

    public static Level current = new Level0();

    //

    private static final String[] completeText = {
            "Well done!",
            "Awesome!",
            "It was easy, huh?",
            "Super!",
            "You are lucky"
    };

    public abstract boolean makePhoto(Pointer cam, List<FlxObject> objects);

    public abstract int newColor();

    public abstract int getMaxFishCount();

    public abstract String getGoalText();

    public String getCompleteText() {
        return completeText[MathUtils.random(completeText.length - 1)];
    }

    public abstract Class<? extends Level> getNextLevel();
}
