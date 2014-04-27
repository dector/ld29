package io.github.dector.ld29;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import com.badlogic.gdx.Gdx;
import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxText;
import org.flixel.event.IFlxCamera;
import org.flixel.plugin.tweens.TweenPlugin;
import org.flixel.plugin.tweens.TweenSprite;

public class SplashState extends BaseState implements IFlxCamera {

    private FlxSprite flag;

    private FlxText titleText;
    private FlxText instructionsText;
    private FlxText controlsText;

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

        controlsText = new FlxText(30, 500, FlxG.width / 2, "F - fullscreen\nR - relax mode");
        controlsText.setFormat(null, 20);
        controlsText.setAlignment("left");
        controlsText.visible = false;

        add(titleText);
        add(instructionsText);
        add(controlsText);
        add(flag);

        fadingTime = Long.MAX_VALUE;

        flashing = true;
        FlxG.flash(0x88000000, .7f, this);
    }

    @Override
    public void update() {
        super.update();

        if (FlxG.keys.R) {
            startLevel(new LevelLast());
        }

        if (FlxG.keys.ESCAPE) {
            Gdx.app.exit();
        } else if (FlxG.keys.justPressedAny() && ! FlxG.keys.F) {
            startLevel(new Level0());
        }

        if (System.currentTimeMillis() >= fadingTime) {

        }
    }

    private void startLevel(Level level) {
        Level.current = level;
        if (! flashing && ! fading) {
            fading = true;
            FlxG.fade(0x88000000, .7f, this);
        }
    }

    @Override
    public void callback() {
        if (flashing) {
            fadingTime = System.currentTimeMillis() + 1000;
            flashing = false;

            instructionsText.visible = true;
            controlsText.visible = true;
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
