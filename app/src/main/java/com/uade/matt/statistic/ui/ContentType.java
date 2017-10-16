package com.uade.matt.statistic.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentType {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Map<String, Item> ITEM_MAP = new HashMap<String, Item>();

    static {
        addItem(new Item("Binomial distribution", "com.uade.matt.statistic.ui.BinomialDistributionActivity", "binomial"));
        addItem(new Item("Normal distribution", "com.uade.matt.statistic.ui.NormalDistributionActivity", "normal"));
        addItem(new Item("Poisson distribution", "com.uade.matt.statistic.ui.PoissonDistributionActivity", "poisson"));
        addItem(new Item("T student distribution", "com.uade.matt.statistic.ui.TDistributionActivity", "t"));
        addItem(new Item("Chi squared distribution", "com.uade.matt.statistic.ui.ChiSquaredDistributionActivity", "chisquared"));
        addItem(new Item("Mean inference", "com.uade.matt.statistic.ui.MeanInferenceActivity", "mean_inference"));
        addItem(new Item("Variance inference", "com.uade.matt.statistic.ui.VarianceInferenceActivity", "variance_inference"));
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class Item {
        public final String id, activityClassName, slug;

        public Item(String id, String activityClassName, String slug) {
            this.activityClassName = activityClassName;
            this.id = id;
            this.slug = slug;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
