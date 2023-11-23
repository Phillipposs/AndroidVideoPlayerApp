package com.example.videoapp.di

import com.app.network.data.RetrofitBuilder
import com.app.network.data.data_source.ApiService
import com.app.network.data.repositories.NetworkVideoRepositoryImpl
import com.app.network.domain.repositories.VideoRepository
import com.app.network.domain.use_cases.GetVideoResponseUseCase
import com.app.network.domain.use_cases.VideosUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNetworkVideoRepository(apiService: ApiService): VideoRepository {
        return NetworkVideoRepositoryImpl(apiService)
    }
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitBuilder.apiService
    }

    @Singleton
    @Provides
    fun provideVideosUseCases(repository: VideoRepository): VideosUseCases {
        return VideosUseCases(getVideoResponseUseCase = GetVideoResponseUseCase(repository))
    }
}