package com.app.network.domain.repositories

import androidx.paging.PagingData
import com.app.network.data.data_source.VideoResponse
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun getVideos(): Flow<PagingData<VideoResponse>>
}