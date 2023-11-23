package com.app.network.data

import com.app.network.data.data_source.ApiService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor.Level

object RetrofitBuilder {
     const val BASE_URL ="https://www.scorebat.com/video-api/v3/feed/"
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = Level.BODY // Set the desired log level
    }

    // Create OkHttpClient with the logging interceptor
    val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    val gson = GsonBuilder().setLenient().create()
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Add the coroutine call adapter
            .build()
    }
    val apiService: ApiService by lazy {
        RetrofitBuilder.retrofit.create(ApiService::class.java)
    }
}
