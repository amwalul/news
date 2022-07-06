package com.example.news.di

import com.example.news.BuildConfig
import com.example.news.data.source.remote.ApiService
import com.example.news.data.source.remote.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = ServiceFactory.createApiService(BuildConfig.NEWS_BASE_URL)
}