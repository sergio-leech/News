package com.example.usatechnologynews.repositories

import com.example.usatechnologynews.api.NewsApi
import com.example.usatechnologynews.model.Article
import kotlinx.coroutines.flow.*
import java.lang.Math.sqrt
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {

   private val getNews: Flow<List<Article>> = flow {
        while (true) {
            val latestNews = newsApi.getNewsList()
            emit(latestNews)
        }
    }
    val getNewsList: Flow<List<Article>> = getNews.catch { e-> e.message }

    fun <T:Number> Flow<T>.sqrts():Flow<Double>{
       return flow<Double> {
            collect { number ->
                val value = sqrt(number.toDouble())
                emit(value)
            }

        }
    }


}