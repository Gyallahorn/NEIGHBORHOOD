package com.esphereal.bair.neighborhood.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class TrainBody {
    @SerializedName("objectId")
    @Expose
    var objectId: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null

    override fun toString(): String {
        return ToStringBuilder(this)
                .append("objectId", objectId)
                .append("date", date).toString()
    }
}