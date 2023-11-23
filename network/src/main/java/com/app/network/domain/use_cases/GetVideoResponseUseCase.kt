package com.app.network.domain.use_cases

import com.app.network.domain.repositories.VideoRepository
import javax.inject.Inject

class GetVideoResponseUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    operator fun invoke() = repository.getVideos()
}