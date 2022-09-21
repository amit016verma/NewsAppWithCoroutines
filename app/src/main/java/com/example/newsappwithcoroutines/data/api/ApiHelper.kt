package com.example.newsappwithcoroutines.data.api

import com.example.newsappwithcoroutines.data.model.News

interface ApiHelper {

    suspend fun getNews() : List<News>

    suspend fun getMoreNews() : List<News>
}