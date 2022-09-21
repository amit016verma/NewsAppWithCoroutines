package com.example.newsappwithcoroutines.data.repository

import com.example.newsappwithcoroutines.data.api.ApiHelper
import com.example.newsappwithcoroutines.data.model.News

class NewsListRepositoryImp(private val apiHelper: ApiHelper) : NewsListRepository {
    override suspend fun getNews(): List<News>  = apiHelper.getNews()

    override suspend fun getMoreNews(): List<News>  = apiHelper.getMoreNews()
}