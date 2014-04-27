package io.github.dector.ld29;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import org.flixel.*;
import org.flixel.event.IFlxCamera;

import java.util.ArrayList;
import java.util.List;

public class GameState extends FlxState {

    private FlxSound shotSound;
    private FlxSound shotWrongSound;

    private FlxSprite background;

    private FlxGroup plants;

    private FlxGroup fishes;

    private FlxGroup hud;
    private FlxText infoText;

    private Pointer pointer;

    private boolean levelDone;

    private long restoreInfoTimestamp;
    private long fadingTimestamp;

    private ActionStarter actionStarter;

    @Override
    public void create() {
        FlxG.debug = true;

        shotSound = FlxG.loadSound("assets/shot.wav");
        shotWrongSound = FlxG.loadSound("assets/shotWrong.wav");

        background = new FlxSprite(0, 0);
        background.loadGraphic("assets/background.png");

        fishes = createFinishes();

        plants = createPlants();

        pointer = new Pointer();

        hud = new FlxGroup();
        infoText = new FlxText(10, 10, FlxG.width - 20);
        infoText.setFormat(null, 25);
        infoText.setText(Level.current.getGoalText());
        hud.add(infoText);

        add(background);
        add(fishes);
        add(getFishesEmmiters());
        add(plants);
        add(hud);
        add(pointer);
    }

    private FlxGroup getFishesEmmiters() {
        FlxGroup emmiters = new FlxGroup();

        for (FlxBasic obj : fishes.members) {
            Fish fish = (Fish) obj;
            emmiters.add(fish.getEmmiter());
        }

        return emmiters;
    }

    private FlxGroup createFinishes() {
        FlxGroup fishes = new FlxGroup();

        for (int i = 0; i < Level.current.getMaxFishCount(); i++) {
            fishes.add(new Fish());
        }

        return fishes;
    }

    private FlxGroup createPlants() {
        FlxGroup plants = new FlxGroup();

        if (actionStarter == null) {
            actionStarter = new ActionStarter();
        }

        int stepX = FlxG.width / Level.current.getMaxPlantsCount();
        int diffX = stepX / 2;

        for (int i = 0; i < Level.current.getMaxPlantsCount(); i++) {
            final FlxSprite plant = new FlxSprite();
            plant.loadGraphic("assets/plant.png", true, false, 12, 28);
            plant.setColor(0x00ff00);
            plant.addAnimation("stand", new int[] { 1 }, 1, true);
            plant.addAnimation("wave", new int[] { 0, 1, 2, 1 }, 1, true);
//            plant.addAnimation("wave_long", new int[] { 0, 0, 1, 1, 2, 2, 1, 1 }, 1, true);
//            plant.addAnimation("wave_left", new int[] { 0, 0, 0, 1, 1 }, 1, true);
//            plant.addAnimation("wave_right", new int[] { 1, 2, 2, 2, 2 }, 1, true);
            actionStarter.startDelayed(new Runnable() {
                @Override
                public void run() {
                    plant.play("wave", true, MathUtils.random(0, 2));
                }
            }, MathUtils.random(0, 2000));
            plant.origin.make(0, 0);
            int scale = MathUtils.random(2, 10);
            plant.width = 12 * scale;
            plant.height = 28 * scale;
            plant.scale.make(scale, scale);
//            plant.x = MathUtils.random(0, FlxG.width);
            float x = i * stepX + MathUtils.random(-1f, 1f) * diffX;
            if (x < 0) x = plant.width / 2;
            if (x > FlxG.width) x = FlxG.width - plant.width / 2;
            plant.x = x;
            plant.y = FlxG.height - plant.height;
            plants.add(plant);
        }

        return plants;
    }

    @Override
    public void update() {
        super.update();

        if (FlxG.keys.ESCAPE) {
            Gdx.app.exit();
        }
        if (FlxG.debug) {
            if (FlxG.keys.R) {
                try {
                    Level.current = Level.current.getClass().newInstance();
                } catch (Exception e) {
                }
                FlxG.resetState();
            }
            if (FlxG.keys.N) {
                nextLevel();
            }

            if (FlxG.keys.P) {
                ((Fish) fishes.getRandom()).bubble();
            }
        }

        if (! levelDone && FlxG.mouse.justPressed()) {
            pointer.makePhoto();

            List<FlxObject> objects = getObjectsOnPhoto();
            Level.ShotResult shotResult = Level.current.makePhoto(pointer, objects);

            switch (shotResult.type) {
                case WRONG:
                    shotWrongSound.play(true);
                    if (shotResult.hasMessage()) {
                        infoText.setText(shotResult.getMessage());
                    } else {
                        infoText.setText(Level.current.getFailText());
                    }
                    restoreInfoTimestamp = System.currentTimeMillis() + 2000;
                    break;
                case CORRECT:
                    shotSound.play(true);
                    if (shotResult.hasMessage()) {
                        infoText.setText(shotResult.getMessage());
                    } else {
                        infoText.setText(Level.current.getCorrectText());
                    }
                    restoreInfoTimestamp = System.currentTimeMillis() + 1000;
                    break;
                case LEVEL_FINISHED:
                    shotSound.play(true);
                    if (shotResult.hasMessage()) {
                        infoText.setText(shotResult.getMessage());
                    } else {
                        infoText.setText(Level.current.getCorrectText());
                    }
                    restoreInfoTimestamp = Long.MAX_VALUE;
                    fadingTimestamp = System.currentTimeMillis() + 1000;
                    levelDone = true;
                    break;
            }
        }

        long currentTimestamp = System.currentTimeMillis();
        if (fadingTimestamp != 0 && fadingTimestamp <= currentTimestamp) {
            nextLevel();
            fadingTimestamp = 0;
        } else if (restoreInfoTimestamp != 0 && restoreInfoTimestamp <= currentTimestamp) {
            infoText.setText(Level.current.getGoalText());
            restoreInfoTimestamp = 0;
        }

        updateFishes();

        actionStarter.update();
    }

    private void nextLevel() {
        final Class<? extends Level> nextLevel = Level.current.getNextLevel();

        if (nextLevel != null) {
            FlxG.fade(0x88000000, 1.5f, new IFlxCamera() {
                @Override
                public void callback() {
                    try {
                        Level.current = nextLevel.newInstance();
                        FlxG.resetState();
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    // FIXME Dirty hack. Because reused fish sometimes is drawing incorrect for updated scale and size values
    private List<Fish> removedFishes = new ArrayList<Fish>();
    private List<Fish> newFishes = new ArrayList<Fish>();

    private void updateFishes() {
        for (FlxBasic obj : fishes.members) {
            Fish fish = (Fish) obj;

            if (! fish.onScreen()) {
                fish.kill();
                removedFishes.add(fish);

                int facing = MathUtils.randomBoolean()
                        ? Fish.LEFT
                        : Fish.RIGHT;
                newFishes.add(new Fish(facing));
            }
        }

        if (! removedFishes.isEmpty()) {
            for (Fish fish : removedFishes) {
                fishes.remove(fish);
            }
            removedFishes.clear();
        }

        if (! newFishes.isEmpty()) {
            for (Fish fish : newFishes) {
                fishes.add(fish);
            }
            newFishes.clear();
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
