package io.github.dector.ld29;

import org.flixel.FlxObject;

import java.util.List;

public abstract class Level {

    public static Level current = new Level0();

    // Level params

    protected int maxFishCount;
    protected int[] fishColors;

    protected String goalText;
    protected String wellDoneText;

    protected Class<? extends Level> nextLevel;

    {
        wellDoneText = "Well done!";
    }

    public abstract boolean makePhoto(Pointer cam, List<FlxObject> objects);
}
