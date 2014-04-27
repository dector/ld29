package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxObject;

import java.util.List;

public abstract class Level {

    public static class ShotResult {
        public ShotResultType type;
        public String message;

        protected ShotResult() {
            type = ShotResultType.WRONG;
        }

        public ShotResultType getType() {
            return type;
        }

        protected void setType(ShotResultType type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        protected void setMessage(String message) {
            this.message = message;
        }

        public boolean hasMessage() {
            return message != null;
        }
    }

    public enum ShotResultType {
        WRONG, CORRECT, LEVEL_FINISHED;
    }

    public static Level current = new Level0();

    private static final String[] completeText = {
            "Well done!",
            "Awesome!",
            "It was easy, huh?",
            "Super!",
            "You are lucky"
    };

    public boolean isObjectFullInPointer(Pointer cam, FlxObject object) {
        return cam.x <= object.x && object.x + object.width <= cam.x + cam.width
                && cam.y <= object.y && object.y + object.height <= cam.y + cam.height;
    }

    public abstract ShotResult makePhoto(Pointer cam, List<FlxObject> objects);

    public abstract int newColor();

    public Fish.Size getSize() {
        return Fish.Size.values()[MathUtils.random(Fish.Size.values().length - 1)];
    }

    public abstract int getMaxFishCount();

    public int getMaxPlantsCount() {
        return 10;
    }

    public abstract String getGoalText();

    public String getCorrectText() {
        return completeText[MathUtils.random(completeText.length - 1)];
    }

    public abstract String getFailText();

    public abstract Class<? extends Level> getNextLevel();
}
