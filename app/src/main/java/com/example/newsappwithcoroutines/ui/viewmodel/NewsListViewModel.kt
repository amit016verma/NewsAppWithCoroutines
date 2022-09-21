package com.example.newsappwithcoroutines.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.newsappwithcoroutines.data.model.News
import com.example.newsappwithcoroutines.data.repository.NewsListRepository
import com.example.newsappwithcoroutines.utils.Resource
import kotlinx.coroutines.launch

class NewsListViewModel(private val newsListRepository: NewsListRepository) : ViewModel() {

    private val newsList = MutableLiveData<Resource<List<News>>>()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            newsList.postValue(Resource.loading(null))
            try {
                val response = newsListRepository.getNews()
                newsList.postValue(Resource.success(response))

            } catch (e: Exception) {
                newsList.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getNews(): LiveData<Resource<List<News>>> {
        return newsList
    }
}