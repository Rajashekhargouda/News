package com.task.bharathagri.newsapp.networking

import com.task.bharathagri.newsapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsApiService {
    @GET
    suspend fun getNews(@Url url: String):Response<NewsResponse>

}