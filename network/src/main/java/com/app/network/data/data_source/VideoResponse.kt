package com.app.network.data.data_source

import com.google.gson.annotations.SerializedName


data class VideoResponse(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("competition")
    val competition: String = "",
    @SerializedName("matchviewUrl")
    val matchViewUrl: String = "",
    @SerializedName("competitionUrl")
    val competitionUrl: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("videos")
    val videos: MutableList<VideoListItem> = mutableListOf(),
)