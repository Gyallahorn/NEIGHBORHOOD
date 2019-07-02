package com.esphereal.bair.funloot.dummy;

import com.esphereal.bair.funloot.R;

import java.util.ArrayList;
import java.util.List;

public class DiscountDummyContent {
    public static class DiscountDummyItem {
        public final String id;
        public final String text;
        public final String detailsText;
        public final int imageUrl;
        public final String discountValue;

        public DiscountDummyItem(String id, String text, String detailsText, int imageUrl, String discountValue) {
            this.id = id;
            this.text = text;
            this.detailsText = detailsText;
            this.imageUrl = imageUrl;
            this.discountValue = discountValue;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static final List<DiscountDummyItem> ITEMS = new ArrayList<>();

    static {
        DiscountDummyItem discountDummyItem1=new DiscountDummyItem("1","Stafit","bla bldasfdsafdsdfbjgldkfgjdf\n lkgjdf;gjd  flgdflghdfljkghdfkjlghdfjlkghdfjkgdjfk \n a bla", R.drawable.stafit,"10%");
        DiscountDummyItem discountDummyItem2=new DiscountDummyItem("2","БЛИЦ","bla bla bla", R.drawable.blitz,"10%");
        DiscountDummyItem discountDummyItem3=new DiscountDummyItem("3","РОСТЕЛЕКОМ","bla bla bla", R.drawable.rosttelecom,"5%");
        DiscountDummyItem discountDummyItem4=new DiscountDummyItem("4","Блин HOUSE","bla bla bla", R.drawable.blin,"10%");
        ITEMS.add(discountDummyItem1);
        ITEMS.add(discountDummyItem2);
        ITEMS.add(discountDummyItem3);
        ITEMS.add(discountDummyItem4);
    }
}
