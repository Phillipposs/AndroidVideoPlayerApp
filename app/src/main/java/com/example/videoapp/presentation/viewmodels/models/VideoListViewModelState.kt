package com.example.videoapp.presentation.viewmodels.models

import com.app.network.data.data_source.VideoListItem
import com.app.network.data.data_source.VideoResponse

data class VideoListViewModelState(val title: String ="",
                                   val competition : String = "",
                                   val matchviewUrl: String = "",
                                   val competitionUrl: String = "",
                                   val thumbnail: String = "",
                                   val date: String = "",
                                   val videos : MutableList<VideoListItem> = mutableListOf(),
                                   val page:Int = 0
                                   )
