package com.uade.matt.statistic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentType {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Map<String, Item> ITEM_MAP = new HashMap<String, Item>();

    static {
        addItem(new Item("Binomial distribution", "com.uade.matt.statistic.ui.BinomialDistributionActivity"));
        addItem(new Item("Normal distribution", "com.uade.matt.statistic.ui.NormalDistributionActivity"));
        addItem(new Item("Poisson distribution", "com.uade.matt.statistic.ui.BinomialDistributionActivity"));
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class Item {
        public final String id, activityClassName;

        public Item(String id, String activityClassName) {
            this.activityClassName = activityClassName;
            this.id = id;
        }
        @Override
        public String toString() {
            return id;
        }
    }
}
