package com.example.usatechnologynews.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usatechnologynews.model.Article
import com.example.usatechnologynews.repositories.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(private val repository: NewsRepository):ViewModel() {

   private val _newsList= MutableLiveData<List<Article>>()
    val newsList:LiveData<List<Article>>
    get() = _newsList

    init {
        getNewsList()
    }
    private fun getNewsList(){
        viewModelScope.launch {
            _newsList.value= repository.getNewsList()
        }
    }
}