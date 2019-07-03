package com.esphereal.bair.neighborhood.NEIGHBORHOOD


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class NibbaPostBody {
    @SerializedName("objectId")
    @Expose
    var objectId: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("problems")
    @Expose
    var problems: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null


    override fun toString(): String {
        return ToStringBuilder(this)
                .append("objectId", objectId)
                .append("title", title)
                .append("problems", problems)
                .append("date", date)
                .toString()
    }
}