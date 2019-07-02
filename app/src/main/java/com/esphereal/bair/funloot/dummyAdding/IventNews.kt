package com.esphereal.bair.funloot.dummyAdding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class IventNews {
    @SerializedName ("_id")
    @Expose
    var id: String? = null
    @SerializedName("news_title")
    @Expose
    var news_title: String? = null
    @SerializedName("news_body")
    @Expose
    var news_body: String? = null

    override fun toString(): String {
        return ToStringBuilder (this)
                .append("news_title", news_title)
                .append("news_body", news_body).toString()
    }
}

