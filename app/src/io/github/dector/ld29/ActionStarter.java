package io.github.dector.ld29;

import java.util.ArrayList;
import java.util.List;

public class ActionStarter {

    private List<Item> itemsToRemove = new ArrayList<Item>();
    private List<Item> itemsList = new ArrayList<Item>();

    public void startDelayed(Runnable action, int delayMs) {
        long currentTime = System.currentTimeMillis();
        long targetTime = currentTime + delayMs;

        itemsList.add(new Item(action, targetTime));
    }

    public void update() {
        if (itemsList.isEmpty()) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        for (int i = 0; i < itemsList.size(); i++) {
            Item item = itemsList.get(i);

            if (item.startTime <= currentTime) {
                item.runnable.run();
                itemsToRemove.add(item);
            }
        }

        if (! itemsToRemove.isEmpty()) {
            for (int i = 0; i < itemsToRemove.size(); i++) {
                itemsList.remove(itemsToRemove.get(i));
            }

            itemsToRemove.clear();
        }
    }

    private static class Item {

        public final Runnable runnable;
        public final long startTime;

        private Item(Runnable runnable, long startTime) {
            this.runnable = runnable;
            this.startTime = startTime;
        }
    }
}
