package com.example.newsappwithcoroutines.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappwithcoroutines.data.api.ApiHelper
import com.example.newsappwithcoroutines.data.repository.NewsListRepositoryImp
import com.example.newsappwithcoroutines.ui.viewmodel.NewsListViewModel
import com.example.newsappwithcoroutines.ui.viewmodel.ParallelNewsListViewModel
import com.example.newsappwithcoroutines.ui.viewmodel.SerialNewsListViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel(NewsListRepositoryImp(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(SerialNewsListViewModel::class.java)) {
            return SerialNewsListViewModel(NewsListRepositoryImp(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(ParallelNewsListViewModel::class.java)) {
            return ParallelNewsListViewModel(NewsListRepositoryImp(apiHelper)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}