package io.lab27.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.lab27.githubuser.datasource.remote.NewsRepository
import io.lab27.githubuser.data.model.Article
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private var _newsResponse = MutableLiveData<List<Article>>()
    val newsResponse: LiveData<List<Article>>
        get() = _newsResponse

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchNews(){
        viewModelScope.launch {
            _isLoading.value = true
            val response = newsRepository.fetchNewsHeadLines()
            _newsResponse.value = response?.articles
            _isLoading.value = false
        }
    }
}