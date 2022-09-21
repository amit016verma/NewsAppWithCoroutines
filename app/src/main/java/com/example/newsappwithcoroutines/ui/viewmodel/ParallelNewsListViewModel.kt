package com.example.newsappwithcoroutines.ui.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.example.newsappwithcoroutines.data.model.News
import com.example.newsappwithcoroutines.data.repository.NewsListRepository
import com.example.newsappwithcoroutines.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ParallelNewsListViewModel(private val newsListRepository: NewsListRepository) : ViewModel() {

    private val newsList = MutableLiveData<Resource<List<News>>>()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
            coroutineScope {
                newsList.postValue(Resource.loading(null))

                val allResponse = mutableListOf<News>()

                val newsResponseDeferred = async { newsListRepository.getNews() }
                val moreNewsResponseDeferred = async { newsListRepository.getMoreNews() }

                val newsResponse = newsResponseDeferred.await()
                val moreNewsResponse = moreNewsResponseDeferred.await()
                allResponse.addAll(newsResponse)
                allResponse.addAll(moreNewsResponse)

                newsList.postValue(Resource.success(allResponse))
            }


            } catch (e: Exception) {
                newsList.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getNews(): LiveData<Resource<List<News>>> {
        return newsList
    }
}