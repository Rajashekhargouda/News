package com.task.bharathagri.newsapp.data.provider

import com.task.bharathagri.newsapp.data.DataMapper
import com.task.bharathagri.newsapp.data.NewsDataValidator
import com.task.bharathagri.newsapp.networking.NewsApiService
import com.task.bharathagri.newsapp.networking.RetrofitHelper
import com.task.bharathagri.newsapp.newsrepository.NewsRepository

object NewsDataProvider {

    fun getNewsRepository(newsDataValidator: NewsDataValidator,
                          dataMapper: DataMapper, newsApiService: NewsApiService):NewsRepository{
        return NewsRepository(newsDataValidator,dataMapper,newsApiService)
    }

    fun getNewsDataValidator(): NewsDataValidator {
        return NewsDataValidator()
    }

    fun getDataMapper(): DataMapper {
        return DataMapper()
    }

    fun getNewsApiService():NewsApiService{
        return RetrofitHelper.getNewsApi()
    }
}