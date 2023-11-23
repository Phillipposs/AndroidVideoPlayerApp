package com.example.videoapp.presentation.viewmodels.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.videoapp.common.ROUTES
import com.example.videoapp.presentation.viewmodels.VideosListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(videosListViewModel: VideosListViewModel) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    var mutex by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = ROUTES.VIDEOS_SCREEN) {
                composable(ROUTES.VIDEOS_SCREEN) {
                    VideosScreen(
                        videosListViewModel,
                        onVideoClick = { navController.navigate(ROUTES.PLAYING_VIDEO_SCREEN) }
                    )
                }
                composable(ROUTES.PLAYING_VIDEO_SCREEN) {

                }
            }
        }

    }

}


