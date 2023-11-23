package com.app.network.data.data_source
import com.app.network.common.Constants.TOKEN
import com.app.network.data.RetrofitBuilder.BASE_URL
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("${BASE_URL}?token=$TOKEN")
    suspend fun getVideos() : NetworkResponse
}
