import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

@Composable
fun ExoPlayerScreen(navController: NavHostController, videoURL : String) {
    BackHandler(true) {
        // Or do nothing
        navController.navigateUp()
    }
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        val context = LocalContext.current
        val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
//        val exoPlayer = ExoPlayer.Builder(context).build()
//            .apply {
//                setMediaItem(fromUri(videoURL))
//                playWhenReady = true
//                prepare()
//            }

        val exoPlayer = remember {
            ExoPlayer.Builder(context)
                .build()
                .apply {
                    val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                    val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                        context,
                        defaultDataSourceFactory
                    )
                    val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(videoURL))

                    setMediaSource(source)
                    prepare()
                }
        }
        exoPlayer.playWhenReady = true
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
        DisposableEffect(key1 =
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                StyledPlayerView(context).apply {
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT)
                }
            }),
            effect = {
                val observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_RESUME -> {
                            Log.e("LIFECYCLE", "resumed")
                            exoPlayer.play()
                        }

                        Lifecycle.Event.ON_PAUSE -> {
                            Log.e("LIFECYCLE", "paused")
                            exoPlayer.stop()
                        }

                        else -> {}
                    }
                }
                val lifecycle =
                    lifecycleOwner.value.lifecycle
                lifecycle.addObserver(observer)
                onDispose {
                    exoPlayer.release()
                    lifecycle.removeObserver(observer)
                }
            }
        )
    }
}