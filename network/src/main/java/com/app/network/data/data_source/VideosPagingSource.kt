package com.app.network.data.data_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.network.common.Constants.PAGE_NUMBER
import retrofit2.HttpException
import java.io.IOException

class VideosPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, VideoResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoResponse> {
        return try {

            var videoResponsesList = apiService.getVideos().response
            Log.d("VideosPagingSource", "load: IS EMPTY ${videoResponsesList.isEmpty()} ")
            val currentPage = params.key ?: 1
            LoadResult.Page(
                data = videoResponsesList,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (videoResponsesList.isEmpty()) null else currentPage.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VideoResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }    }

}