package com.example.news.data.source.remote

import com.example.news.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
                    .build()

                chain.proceed(chain.request().newBuilder().url(url).build())
            }
            .build()
    }

    fun createApiService(baseUrl: String): ApiService = with(Retrofit.Builder()) {
        baseUrl(baseUrl)
        client(createOkHttpClient())
        addConverterFactory(GsonConverterFactory.create())
        build()
    }.create(ApiService::class.java)
}