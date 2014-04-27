package io.github.dector.ld29;

import com.badlogic.gdx.math.MathUtils;
import org.flixel.FlxEmitter;
import org.flixel.FlxG;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;

public class Fish extends FlxSprite {

    private static final int FISH_W = 28;
    private static final int FISH_H = 12;

    public enum Size {
        SMALL('A', 1, 1), MEDIUM('B', 2, 2), BIG('C', 3, 3);

        public final char scaleFactor;

        public final float scaleX;
        public final float scaleY;

        Size(char scaleFactor, float scaleX, float scaleY) {
            this.scaleFactor = scaleFactor;

            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }
    }

    private Size size;
    private FlxPoint emitterOffset;

    public boolean hasPhoto;

    private long lastBubbleTime;
    private FlxEmitter emitter;

    public Fish() {
        this(NONE);
    }

    public Fish(int facing) {
        emitterOffset = new FlxPoint();

        emitter = new FlxEmitter();
        emitter.setMaxSize(5);

        for (int i = 0; i < emitter.getMaxSize(); i++) {
            emitter.add(new Bubble());
        }

        loadGraphic("assets/fish_t.png", false, true, 28, 12);

        setFacing(facing);
        init();
    }

    private void init() {
        hasPhoto = false;

        /*int color = 0xff << 24
                | MathUtils.random(0x00, 0xff) << 16
                | MathUtils.random(0x00, 0xff) << 8
                | MathUtils.random(0x00, 0xff);*/
//        int color = 0xff000000
//                | Level.current.newColor();
        setColor(Level.current.newColor());

        y = MathUtils.random(FlxG.height);

        int facing = getFacing();
        switch (facing) {
            case NONE:
                x = MathUtils.random(FlxG.width);

                facing = MathUtils.randomBoolean() ? LEFT : RIGHT;
                setFacing(facing);
                break;
            case LEFT:
                x = FlxG.width - 1;
                break;
            case RIGHT:
                x = 1 - width;
                break;
            default:
                return;
        }

        if (facing == LEFT) {
            velocity.x = - MathUtils.random(10, 100);
        } else {
            velocity.x = MathUtils.random(10, 100);
        }

        Size fishSize = Level.current.getSize();
        setSize(fishSize);

        dirty = true;

        lastBubbleTime = System.currentTimeMillis() + MathUtils.random(1000, 4000);
    }

    /*public void setColor(int color) {
        if (getColor() != color) {
            super.setColor(color);
        }
    }*/

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        width = FISH_W * size.scaleX;
        height = FISH_H * size.scaleY;

        scale.x = size.scaleX;
        scale.y = size.scaleY;

        origin.x = 0;
        origin.y = 0;
        offset.x = 0;
        offset.y = 0;

        emitterOffset.x = getFacing() == LEFT
                ? size.scaleX * 5
                : width - size.scaleX * 5 ;
        emitterOffset.y = height - size.scaleY * 4;

        this.size = size;
    }

    @Override
    public void update() {
        super.update();

        emitter.x = x + emitterOffset.x;
        emitter.y = y + emitterOffset.y;

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
