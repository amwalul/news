package com.example.news.data.source.remote

import com.example.news.data.source.remote.response.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String
    ): NewsListResponse
}