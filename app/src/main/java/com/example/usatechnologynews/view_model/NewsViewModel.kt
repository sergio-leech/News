package com.example.usatechnologynews.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.usatechnologynews.model.Article
import com.example.usatechnologynews.model.ListType
import com.example.usatechnologynews.repositories.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(private val repository: NewsRepository):ViewModel() {

    val newsList= liveData {
        emit(repository.getNewsList())
  }

    private val _newsListType: MutableLiveData<ListType> = MutableLiveData()
    val newsListType: LiveData<ListType>
        get() = _newsListType

    fun getNewsById(id:String): Article? = newsList.value?.find { news -> news.url == id }

    fun setEpisodesListType(type: ListType) {
        _newsListType.value = type
    }
}