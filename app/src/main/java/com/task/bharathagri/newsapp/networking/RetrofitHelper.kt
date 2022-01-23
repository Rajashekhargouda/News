package com.task.bharathagri.newsapp.networking

object RetrofitHelper {

    fun getNewsApi():NewsApiService{
        return RetrofitModel.getNewsApiService()
    }

    fun getNewsUrl(pageNum:Int):String{
       return "${RetrofitModel.getBaseUrl()}everything?domains=techcrunch.com,thenextweb" +
               ".com&apiKey=09786f71377c4810a7ffe7a3c4add6cf&page=$pageNum"
    }
}