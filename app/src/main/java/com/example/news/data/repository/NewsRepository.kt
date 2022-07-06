package com.example.news.data.repository

import com.example.news.data.Resource
import com.example.news.data.source.remote.ApiService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getNews(country: String, category: String) = Resource.fromSource(
        sourceCall = { apiService.getNews(country, category) }
    )
}