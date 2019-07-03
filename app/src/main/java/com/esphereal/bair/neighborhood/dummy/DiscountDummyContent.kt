package com.esphereal.bair.neighborhood.dummy

import com.esphereal.bair.funloot.R

import java.util.ArrayList

object DiscountDummyContent {

    val ITEMS: MutableList<DiscountDummyItem> = ArrayList()

    class DiscountDummyItem(val id: String, val text: String, val detailsText: String, val imageUrl: Int, val discountValue: String) {

        override fun toString(): String {
            return text
        }
    }

    init {
        val discountDummyItem1 = DiscountDummyItem("1", "Stafit", "bla bldasfdsafdsdfbjgldkfgjdf\n lkgjdf;gjd  flgdflghdfljkghdfkjlghdfjlkghdfjkgdjfk \n a bla", R.drawable.stafit, "10%")
        val discountDummyItem2 = DiscountDummyItem("2", "БЛИЦ", "bla bla bla", R.drawable.blitz, "10%")
        val discountDummyItem3 = DiscountDummyItem("3", "РОСТЕЛЕКОМ", "bla bla bla", R.drawable.rosttelecom, "5%")
        val discountDummyItem4 = DiscountDummyItem("4", "Блин HOUSE", "bla bla bla", R.drawable.blin, "10%")
        ITEMS.add(discountDummyItem1)
        ITEMS.add(discountDummyItem2)
        ITEMS.add(discountDummyItem3)
        ITEMS.add(discountDummyItem4)
    }
}
