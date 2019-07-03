package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.media.Rating
import com.esphereal.bair.funloot.R.drawable


object NibbaList {
    val ITEMS: MutableList<NibbaListItem> = ArrayList()

    class NibbaListItem(val title: String,
                        val body: String,
                        val imageUrl: Int,
                        val UserName:String,
                        val Rating: Int) {
        private val expanded: Boolean = false
        fun toTitle(): String {
            return title
        }

        fun toBody(): String {
            return body
        }

        fun toUser(): String {
            return UserName
        }
        fun toRating(): Int {

            return Rating
        }

    }

    init {
        val nibbaListItem1 = NibbaListItem("Хватит мусорить!!!", "Возле нашего подъезда скопилась гора мусора, предлагаю установить баки...", drawable.trash, "Иванов Иван", Rating=0)
        val nibbaListItem2 = NibbaListItem("Продам гараж", "Продам Гараж за 100р", drawable.back, "Иванов Иван", Rating=0)
        val nibbaListItem3 = NibbaListItem("Продам гараж", "Продам Гараж за 100р", drawable.blin, "Иванов Иван", Rating=0)
        val nibbaListItem4 = NibbaListItem("Продам гараж", "Продам Гараж за 100р", drawable.back, "Иванов Иван", Rating=0)
        ITEMS.add(nibbaListItem1)
        ITEMS.add(nibbaListItem2)
        ITEMS.add(nibbaListItem3)
        ITEMS.add(nibbaListItem4)
    }
}
