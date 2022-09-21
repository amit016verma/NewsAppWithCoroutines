package com.example.newsappwithcoroutines.data.api

import com.example.newsappwithcoroutines.data.model.News

class ApiHelperImp(val apiService: ApiService) : ApiHelper {

    override suspend fun getNews(): List<News> = apiService.getNews()

    override suspend fun getMoreNews(): List<News> = apiService.getMoreNews()

}
