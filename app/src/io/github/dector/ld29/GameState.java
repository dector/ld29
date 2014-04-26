package io.github.dector.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import org.flixel.*;
import org.flixel.event.IFlxCamera;

import java.util.ArrayList;
import java.util.List;

public class GameState extends FlxState {

    private FlxSound shotSound;

    private FlxSprite background;

    private FlxGroup fishes;

    private FlxGroup hud;
    private FlxText infoText;

    private Pointer pointer;

    private boolean levelDone;

    @Override
    public void create() {
        FlxG.debug = true;

//        shotSound = FlxG.loadSound("assets/shot.wav");

        background = new FlxSprite(0, 0);
        background.loadGraphic("assets/background.png");

        fishes = createFinishes();

        pointer = new Pointer();

        hud = new FlxGroup();
        infoText = new FlxText(10, 10, FlxG.width - 20);
        infoText.setFormat(null, 25);
        infoText.setText(Level.current.getGoalText());
        hud.add(infoText);

        add(background);
        add(fishes);
        add(hud);
        add(pointer);
    }

    private FlxGroup createFinishes() {
        fishes = new FlxGroup();

        for (int i = 0; i < Level.current.getMaxFishCount(); i++) {
            fishes.add(new Fish());
        }

        return fishes;
    }

    @Override
    public void update() {
        super.update();

        if (FlxG.keys.ESCAPE) {
            Gdx.app.exit();
        }
        if (FlxG.debug) {
            if (FlxG.keys.R) {
                FlxG.resetState();
            }
        }

        if (! levelDone && FlxG.mouse.justPressed()) {
//            shotSound.play(true);

            pointer.makePhoto();

            List<FlxObject> objects = getObjectsOnPhoto();
            levelDone = Level.current.makePhoto(pointer, objects);

            if (levelDone) {
                infoText.setText(Level.current.getCompleteText());

                FlxG.fade(0x88000000, 1.5f, new IFlxCamera() {
                    @Override
                    public void callback() {
                        if (Level.current.getNextLevel() != null) {
                            try {
                                Level.current = Level.current.getNextLevel().newInstance();
                            } catch (Exception e) {
                            }
                        } else {
                            FlxG.switchState(new SplashState());
                        }
                    }
                });
            }
        }

        updateFishes();
    }

    private void updateFishes() {
        for (FlxBasic obj : fishes.members) {
            Fish fish = (Fish) obj;

            if (! fish.onScreen()) {
                fish.init();
                Fish.StartPosition start = MathUtils.randomBoolean()
                        ? Fish.StartPosition.LEFT
                        : Fish.StartPosition.RIGHT;
                fish.startFrom(start);
            }
        }
    }

    public List<FlxObject> getObjectsOnPhoto() {
        List<FlxObject> objects = new ArrayList<FlxObject>();

        for (FlxBasic obj : fishes.members) {
            Fish fish = (Fish) obj;

            if (pointer.overlaps(fish)) {
                objects.add(fish);
            }
        }

        return objects;
    }
}