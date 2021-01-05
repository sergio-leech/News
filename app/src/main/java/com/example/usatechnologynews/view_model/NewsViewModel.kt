package com.example.usatechnologynews.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usatechnologynews.model.Article
import com.example.usatechnologynews.model.ListType
import com.example.usatechnologynews.repositories.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(private val repository: NewsRepository):ViewModel() {

   private val _newsList= MutableLiveData<List<Article>>()
    val newsList:LiveData<List<Article>>
    get() = _newsList

    private val _newsListType: MutableLiveData<ListType> = MutableLiveData()
    val newsListType: LiveData<ListType>
        get() = _newsListType

    init {
        getNewsList()
    }
    private fun getNewsList(){
        viewModelScope.launch {
            _newsList.value= repository.getNewsList()
        }
    }

    fun getNewsById(id:String): Article? = newsList.value?.find { news -> news.url == id }

    fun setEpisodesListType(type: ListType) {
        _newsListType.value = type
    }
}