package com.example.usatechnologynews.api


import com.example.usatechnologynews.model.NewsResponse
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class NewsApi @Inject constructor(private val httpClient: HttpClient) {
    private  val apiKey = "0a9481caf647403ba0fd388323aa95c2"
    private val url = "http://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=$apiKey"

    suspend fun getNewsList() =
        httpClient.get<NewsResponse>(url).articles
}
