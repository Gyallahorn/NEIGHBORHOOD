package com.esphereal.bair.neighborhood.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample text for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
   /*     for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }*/
        DummyItem news1 = new DummyItem("0", "Приняты рекомендации по развитию скандинавской ходьбы и подготовке к 100-летию физкультурного движения в Якутии", "news1");
        addItem(news1);
        DummyItem news2 = new DummyItem("1", "ЯКУТИЯ ГОТОВИТСЯ К VIII МЕЖДУНАРОДНЫМ СПОРТИВНЫМ ИГРАМ «ДЕТИ АЗИИ»", "news2");
        addItem(news2);
        DummyItem news3 = new DummyItem("2", "Якутия намерена организовать лучший в истории Кубок мира по вольной борьбе", "news3");
        addItem(news3);
        DummyItem news4 = new DummyItem("3", "Боксеры Якутии избрали новый состав Федерации", "news4");
        addItem(news4);
        DummyItem news5 = new DummyItem("4", "На играх «Дети Азии» стартовали соревнования по фигурному катанию", "news5");
        addItem(news5);


    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Новость 1" + position, "news1");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore detailsText information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of text.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }


}
