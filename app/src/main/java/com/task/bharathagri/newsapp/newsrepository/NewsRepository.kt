package com.task.bharathagri.newsapp.newsrepository

import com.task.bharathagri.newsapp.data.DataMapper
import com.task.bharathagri.newsapp.data.NewsDataValidator
import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.UiResponseModel
import com.task.bharathagri.newsapp.networking.NewsApiService
import com.task.bharathagri.newsapp.networking.RetrofitHelper

class NewsRepository(var newsDataValidator: NewsDataValidator,
                     var dataMapper: DataMapper, var newsApiService: NewsApiService) {
    suspend fun getNews(page:Int):UiResponseModel<List<INewsUiModel>>{
        val url = RetrofitHelper.getNewsUrl(page)
        var response = newsApiService.getNews(url)
        var result = newsDataValidator.validateNewsResponse(response,dataMapper)
        return result
    }
}