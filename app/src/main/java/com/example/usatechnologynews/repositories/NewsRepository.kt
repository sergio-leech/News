package com.example.usatechnologynews.repositories

import com.example.usatechnologynews.api.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi:NewsApi){
    suspend fun getNewsList()=newsApi.getNewsList()
}