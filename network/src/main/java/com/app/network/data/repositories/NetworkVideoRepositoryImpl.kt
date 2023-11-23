package com.app.network.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.network.data.data_source.ApiService
import com.app.network.data.data_source.VideoResponse
import com.app.network.data.data_source.VideosPagingSource
import com.app.network.domain.repositories.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkVideoRepositoryImpl @Inject constructor(private val apiService: ApiService) : VideoRepository {
    override fun getVideos(): Flow<PagingData<VideoResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
            ),
            pagingSourceFactory = {
                 VideosPagingSource(apiService)
            }
        ).flow
    }
}