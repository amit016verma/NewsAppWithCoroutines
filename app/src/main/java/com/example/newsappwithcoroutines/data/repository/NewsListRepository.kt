package com.example.newsappwithcoroutines.data.repository

import com.example.newsappwithcoroutines.data.model.News

interface NewsListRepository {

    suspend fun getNews() : List<News>

    suspend fun getMoreNews() : List<News>
}