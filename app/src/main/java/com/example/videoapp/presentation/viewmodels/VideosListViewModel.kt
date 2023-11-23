package com.example.videoapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.network.data.data_source.VideoResponse
import com.app.network.domain.use_cases.VideosUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class VideosListViewModel @Inject constructor(
    val videosUseCases: VideosUseCases
) : ViewModel() {
    val videoURL = mutableStateOf("")
    val selectedVideo = mutableStateOf(VideoResponse())
    private val TAG = "VideosListViewModel"

    private val _videosState: MutableStateFlow<PagingData<VideoResponse>> =
        MutableStateFlow(value = PagingData.empty())
    val videosState: MutableStateFlow<PagingData<VideoResponse>>get() = _videosState

    fun getVideos() {
        viewModelScope.launch {
            videosUseCases.getVideoResponseUseCase().cachedIn(viewModelScope)
                .collect {
                    _videosState.value = it
                }
        }
    }

    fun getVideoUrl(html: String) {
        val doc = Jsoup.parse(html)
        var link = doc.body().select("iframe").attr("src")
        //Since API I chose uses live streaming and uses embedded iframe links which plays youtube links, Youtube API is needed for vide URL to be extracted.
        //Above mentioned surpasses the scope of this example so I will return the link of the same video I found on the internet to demonstrate functionality.
        videoURL.value = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    }
}