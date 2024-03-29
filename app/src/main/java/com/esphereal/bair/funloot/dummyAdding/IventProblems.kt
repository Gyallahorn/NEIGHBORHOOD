package com.esphereal.bair.funloot.dummyAdding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

//This for parse data from MONGODB
class IventProblems {
    @SerializedName ("_id")
    @Expose
    var id: String? = null
    @SerializedName("title")
    @Expose
    var problems_title: String? = null
    @SerializedName("body")
    @Expose
    var problems_body: String? = null

    override fun toString(): String {
        return ToStringBuilder (this)
                .append("news_title", problems_title)
                .append("problems_body", problems_body).toString()
    }
}

