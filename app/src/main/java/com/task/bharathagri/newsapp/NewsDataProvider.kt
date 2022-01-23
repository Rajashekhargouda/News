package com.task.bharathagri.newsapp

import com.task.bharathagri.newsapp.newsrepository.NewsRepository

object NewsDataProvider {

    fun getNewsRepository():NewsRepository{
        return NewsRepository()
    }
}