package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxEmitter;
import org.flixel.FlxG;
import org.flixel.FlxSprite;

public class Fish extends FlxSprite {

    public enum StartPosition {
        RANDOM, LEFT, RIGHT
    }

    private long lastBubbleTime;
    private FlxEmitter emitter;

    public Fish() {
        emitter = new FlxEmitter();
        emitter.setMaxSize(5);

        for (int i = 0; i < emitter.getMaxSize(); i++) {
            emitter.add(new Bubble());
        }

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

        lastBubbleTime = System.currentTimeMillis() + MathUtils.random(1000, 4000);
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

    @Override
    public void update() {
        super.update();

        emitter.x = x + (getFacing() < 0 ? 10 : (width - 10));
        emitter.y = y + height / 2;

        long currentTime = System.currentTimeMillis();
        if (lastBubbleTime <= currentTime) {
            bubble();
            lastBubbleTime = currentTime + MathUtils.random(2000, 5000);
        }
    }

    /*debug*/ void bubble() {
        emitter.setXSpeed(-10, 10);
        emitter.setYSpeed(-20, -70);
        emitter.setRotation(10, 45);
        emitter.start(false, 5f, .1f, MathUtils.random(1, emitter.getMaxSize()));
    }

    public FlxEmitter getEmmiter() {
        return emitter;
    }
}
