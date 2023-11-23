package com.example.videoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.network.domain.use_cases.VideosUseCases
import com.example.videoapp.presentation.viewmodels.VideosListViewModel
import com.example.videoapp.presentation.viewmodels.screens.MainScreen
import com.example.videoapp.ui.theme.VideoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    private val videosListViewModel: VideosListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
                delay(1000)
                videosListViewModel.getVideos()
            }

        setContent {
            VideoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(videosListViewModel = videosListViewModel)
                }
            }
        }
    }
}