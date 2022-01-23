package com.task.bharathagri.newsapp.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModel {
    private var baseUrl = "https://newsapi.org/v2/"
    private var retrofit:Retrofit? = null

    private fun getRetrofit():Retrofit{
        return if (retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()
            retrofit!!
        }else{
            retrofit!!
        }
    }

    private fun getOkHttpClient():OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(interceptor)
        return okHttpBuilder.build()
    }

    fun getNewsApiService(): NewsApiService {
        return getRetrofit().create(NewsApiService::class.java)
    }
    fun getBaseUrl():String {
        return baseUrl
    }
}