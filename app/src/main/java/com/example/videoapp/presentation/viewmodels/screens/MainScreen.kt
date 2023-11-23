package com.example.videoapp.presentation.viewmodels.screens

import ExoPlayerScreen
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videoapp.common.ROUTES
import com.example.videoapp.presentation.viewmodels.VideosListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(videosListViewModel: VideosListViewModel) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = ROUTES.VIDEOS_SCREEN) {
                composable(ROUTES.VIDEOS_SCREEN) {
                    VideosScreen(
                        videosListViewModel
                    ) { video ->
                        videosListViewModel.selectedVideo.value = video
                        videosListViewModel.getVideoUrl(video.videos[0].embed)
                        navController.navigate(ROUTES.PLAYING_VIDEO_SCREEN)
                    }
                }
                composable(ROUTES.PLAYING_VIDEO_SCREEN) {
                    ExoPlayerScreen(navController,videosListViewModel.videoURL.value, videosListViewModel.selectedVideo.value.title,videosListViewModel.selectedVideo.value.competition)
                }
            }
        }

    }

}


