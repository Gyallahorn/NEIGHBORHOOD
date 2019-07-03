package com.esphereal.bair.neighborhood.dummyAdding


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder

class ScoreBody {
    @SerializedName("objectId")
    @Expose
    var objectId: String? = null
    @SerializedName("team_1_name")
    @Expose
    var team_1_name: String? = null
    @SerializedName("team_2_name")
    @Expose
    var team_2_name: String? = null
    @SerializedName("team_1_score")
    @Expose
    var team_1_score: String? = null
    @SerializedName("team_2_score")
    @Expose
    var team_2_score: String? = null


    override fun toString(): String {
        return ToStringBuilder(this)
                .append("objectId", objectId)
                .append("team_1_name", team_1_name)
                .append("team_2_name", team_2_name)
                .append("team_1_score", team_1_score)
                .append("team_2_score", team_2_score)
                .toString()
    }
}