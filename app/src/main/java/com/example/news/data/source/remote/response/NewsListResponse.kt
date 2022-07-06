package com.example.news.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<ArticleData>
)