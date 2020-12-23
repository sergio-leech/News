package com.example.usatechnologynews.api

import com.example.usatechnologynews.model.NewsResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*

object NewsApi {
    private const val API_KEY = "0a9481caf647403ba0fd388323aa95c2"
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    suspend fun getNewsList() =
        client.request<NewsResponse>("http://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=$API_KEY").articles
}