package com.esphereal.bair.neighborhood.dummyAdding

import java.util.ArrayList

object IventData {


    val ITEMS: MutableList<IventDataItem> = ArrayList()

    class IventDataItem(val title: String, val body: String) {

        override fun toString(): String {
            return title
        }
    }

    init {
        val iventDataItem1 = IventDataItem("fwefkwemfk", "Stafit")
        val iventDataItem2 = IventDataItem("ololo", "wlwlw")
        val iventDataItem3 = IventDataItem("dfkjgk", "Ssdft")
        ITEMS.add(iventDataItem1)
        ITEMS.add(iventDataItem2)
        ITEMS.add(iventDataItem3)

    }

}

