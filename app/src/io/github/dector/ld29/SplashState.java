package io.github.dector.ld29;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxCamera;
import org.flixel.plugin.tweens.TweenPlugin;
import org.flixel.plugin.tweens.TweenSprite;

public class SplashState extends FlxState implements IFlxCamera {

    private FlxSprite flag;

    private FlxText titleText;
    private FlxText instructionsText;

    private boolean flashing;
    private boolean fading;
    private long fadingTime;

    @Override
    public void create() {
        FlxG.addPlugin(TweenPlugin.class);

        FlxG.setBgColor(0xff353535);

        flag = new FlxSprite(550, 400);
        flag.loadGraphic("assets/flag.png");

        titleText = new FlxText(0, 100, FlxG.width, "Blue waves");
        titleText.setFormat(null, 80);
        titleText.setAlignment("center");

        instructionsText = new FlxText(0, 300, FlxG.width, "Press ANY key to continue");
        instructionsText.setFormat(null, 20);
        instructionsText.setAlignment("center");
        instructionsText.visible = false;

        add(titleText);
        add(instructionsText);
        add(flag);

        fadingTime = Long.MAX_VALUE;

        flashing = true;
        FlxG.flash(0x88000000, .7f, this);
    }

    @Override
    public void update() {
        super.update();

        if (FlxG.keys.justPressedAny()) {
            if (! flashing && ! fading) {
                fading = true;
                FlxG.fade(0x88000000, .7f, this);
            }
        }

        if (System.currentTimeMillis() >= fadingTime) {

        }
    }

    @Override
    public void callback() {
        if (flashing) {
            fadingTime = System.currentTimeMillis() + 1000;
            flashing = false;

            instructionsText.visible = true;
            Tween.to(instructionsText, TweenSprite.SCALE_XY, .7f)
                .target(1.2f, 1.2f)
                .ease(TweenEquations.easeNone)
                .repeatYoyo(Tween.INFINITY, 0)
                .build()
                .start(TweenPlugin.manager);
        } else if (fading) {
            nextState();
        }
    }

    @Override
    public void destroy() {
        super.destroy();

        FlxG.removePluginType(TweenPlugin.class);
    }

    private void nextState() {
        FlxG.switchState(new GameState());
    }
}
