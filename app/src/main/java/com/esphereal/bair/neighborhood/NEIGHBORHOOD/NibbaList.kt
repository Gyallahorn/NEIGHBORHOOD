package com.esphereal.bair.funloot.NEIGHBORHOOD

import android.media.Image
import com.esphereal.bair.funloot.R
import com.esphereal.bair.funloot.R.drawable

object NibbaList {
    val ITEMS: MutableList<NibbaListItem> = ArrayList()

    class NibbaListItem(val title: String, val body: String, val imageUrl: Int) {
        override fun toString(): String {
            return title
        }
    }

    init {
        val nibbaListItem1 = NibbaListItem("Продам гараж", "Продам Гараж за 100р", drawable.blin)
        val nibbaListItem2 = NibbaListItem("Продам гараж", "Продам Гараж за 100р", drawable.back)
        val nibbaListItem3 = NibbaListItem("Продам гараж", "Продам Гараж за 100р", drawable.blin)
        val nibbaListItem4 = NibbaListItem("Продам гараж", "Продам Гараж за 100р", drawable.back)
        ITEMS.add(nibbaListItem1)
        ITEMS.add(nibbaListItem2)
        ITEMS.add(nibbaListItem3)
        ITEMS.add(nibbaListItem4)
    }
}