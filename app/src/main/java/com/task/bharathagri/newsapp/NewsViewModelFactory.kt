package com.task.bharathagri.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.bharathagri.newsapp.newsrepository.NewsRepository

class NewsViewModelFactory(var newsRepository: NewsRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(newsRepository ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}