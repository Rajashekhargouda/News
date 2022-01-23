package com.task.bharathagri.newsapp.newsrepository

import com.task.bharathagri.newsapp.model.NewsResponse
import com.task.bharathagri.newsapp.networking.RetrofitHelper
import retrofit2.Response

class NewsRepository {
    suspend fun getNews(page:Int):Response<NewsResponse>{
        val url = RetrofitHelper.getNewsUrl(page)
        return RetrofitHelper.getNewsApi().getNews(url)
    }
}