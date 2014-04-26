package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxG;
import org.flixel.FlxSprite;

public class Fish extends FlxSprite {

    public enum StartPosition {
        RANDOM, LEFT, RIGHT
    }

    public Fish() {
        init();
        startFrom(StartPosition.RANDOM);
    }

    public void init() {
        height = MathUtils.random(10, 30);
        width = height * MathUtils.random(1.5f, 3f);

        /*int color = 0xff << 24
                | MathUtils.random(0x00, 0xff) << 16
                | MathUtils.random(0x00, 0xff) << 8
                | MathUtils.random(0x00, 0xff);*/
        int color = 0xff000000
                | Level.current.newColor();
        setColor(color);
    }

    public void setColor(int color) {
        if (getColor() != color) {
            super.setColor(color);
            makeGraphic((int) width, (int) height, color);
        }
    }

    public void startFrom(StartPosition start) {
        y = MathUtils.random(FlxG.height);

        int facing;

        switch (start) {
            case RANDOM:
                x = MathUtils.random(FlxG.width);

                facing = MathUtils.randomBoolean() ? 1 : -1;

                velocity.x = facing * MathUtils.random(10, 100);
                break;
            case LEFT:
                x = - width + 1;
                facing = 1;
                break;
            case RIGHT:
                x = FlxG.width - 1;
                facing = -1;
                break;
            default:
                return;
        }

        setFacing(facing);
        velocity.x = facing * MathUtils.random(10, 100);
    }
}
