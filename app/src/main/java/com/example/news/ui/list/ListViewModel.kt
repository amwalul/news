package com.example.news.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.Resource
import com.example.news.data.repository.NewsRepository
import com.example.news.data.source.remote.response.NewsListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsResourceLiveData = MutableLiveData<Resource<NewsListResponse>>()
    val newsResourceLiveData: LiveData<Resource<NewsListResponse>> get() = _newsResourceLiveData

    fun getNews(country: String, category: String) = viewModelScope.launch {
        newsRepository.getNews(country, category).collect { resource ->
            _newsResourceLiveData.value = resource
        }
    }
}