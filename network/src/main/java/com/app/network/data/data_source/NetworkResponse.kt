package com.app.network.data.data_source

import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    val response: List<VideoResponse> = listOf()
)