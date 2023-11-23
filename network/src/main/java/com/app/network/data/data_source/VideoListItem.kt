package com.app.network.data.data_source

import com.google.gson.annotations.SerializedName

data class VideoListItem(
    @SerializedName("id")
    val id:String = "",
    @SerializedName("title")
    val title:String = "",
    @SerializedName("embed")
    val embed: String = ""
)
