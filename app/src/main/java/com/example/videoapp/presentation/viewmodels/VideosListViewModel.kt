package com.example.videoapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.network.data.data_source.VideoListItem
import com.app.network.data.data_source.VideoResponse
import com.app.network.domain.use_cases.VideosUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosListViewModel @Inject constructor(
    private val videosUseCases: VideosUseCases
) : ViewModel() {
    private val TAG = "VideosListViewModel"

    private val _videosState: MutableStateFlow<PagingData<VideoResponse>> =
        MutableStateFlow(value = PagingData.empty())
    val videosState: MutableStateFlow<PagingData<VideoResponse>>get() = _videosState
    //fun getVideos(): Flow<PagingData<VideoResponse>> = videosUseCases.getVideoResponseUseCase().cachedIn(viewModelScope)

    fun getVideos() {
        viewModelScope.launch {
            videosUseCases.getVideoResponseUseCase()
                .collect {
                    _videosState.value = it
                }
        }
    }
}