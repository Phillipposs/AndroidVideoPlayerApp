package com.example.videoapp.presentation.viewmodels

import com.app.network.domain.use_cases.VideosUseCases
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotSame
import org.junit.Before
import org.junit.Test

class VideosListViewModelTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var viewModel: VideosListViewModel
    private lateinit var useCases: VideosUseCases


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        useCases = mockk()

        viewModel = VideosListViewModel(useCases)

    }

    @After
    fun tearDown() {
        clearAllMocks()
        unmockkAll()
    }
    @Test

    fun videosListViewModel_getVideos_Success() = runTest {
        every {
            viewModel.videosUseCases.getVideoResponseUseCase()
        } returns flow {
            emit((mockk(relaxed = true)))
        }

        viewModel.getVideos()
        coVerify(exactly = 1) { viewModel.videosUseCases.getVideoResponseUseCase() }
        assertNotSame(null, viewModel.videosState.value)

    }
}