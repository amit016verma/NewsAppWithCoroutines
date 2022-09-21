package com.example.newsappwithcoroutines.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.newsappwithcoroutines.data.model.News
import com.example.newsappwithcoroutines.data.repository.NewsListRepository
import com.example.newsappwithcoroutines.utils.Resource
import kotlinx.coroutines.launch

class SerialNewsListViewModel(private val newsListRepository: NewsListRepository) : ViewModel() {

    private val newsList = MutableLiveData<Resource<List<News>>>()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            newsList.postValue(Resource.loading(null))
            try {
                val allResponse = mutableListOf<News>()
                val newsResponse = newsListRepository.getNews()
                val moreNewsResponse = newsListRepository.getMoreNews()
                allResponse.addAll(newsResponse)
                allResponse.addAll(moreNewsResponse)

                newsList.postValue(Resource.success(allResponse))

            } catch (e: Exception) {
                newsList.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getNews(): LiveData<Resource<List<News>>> {
        return newsList
    }
}