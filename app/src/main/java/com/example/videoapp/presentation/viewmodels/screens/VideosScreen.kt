package com.example.videoapp.presentation.viewmodels.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.app.network.data.data_source.VideoResponse
import com.example.videoapp.R
import com.example.videoapp.common.ErrorMessage
import com.example.videoapp.common.LoadingNextPageItem
import com.example.videoapp.common.PageLoader
import com.example.videoapp.presentation.viewmodels.VideosListViewModel

@Composable
fun VideosScreen(
    viewModel: VideosListViewModel,
    onVideoClick: (VideoResponse) -> Unit
) {
    val videosPagingItems: LazyPagingItems<VideoResponse> =
        viewModel.videosState.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(videosPagingItems.itemCount) { index ->
                Column(Modifier.clickable {
                    onVideoClick(videosPagingItems[index]!!)
                }) {
                    AsyncImage(
                        model = videosPagingItems[index]!!.thumbnail,
                        //modifier = Modifier.height(200.dp).fillParentMaxWidth(),
                        placeholder = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "The delasign logo",
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = videosPagingItems[index]!!.title,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                }

            }
            videosPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { PageLoader(modifier = Modifier.fillMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = videosPagingItems.loadState.refresh as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier.fillMaxSize(),
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = videosPagingItems.loadState.append as LoadState.Error
                        item {
                            ErrorMessage(
                                modifier = Modifier,
                                message = error.error.localizedMessage!!,
                                onClickRetry = { retry() })
                        }
                    }
                }
            }

        }
    )
}