package com.task.bharathagri.newsapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(var author:String,
                   var title:String,
                   var description:String,
                   var url:String,
                   var urlToImage:String,
                   var publishedAt:String,
                   var content:String,
                   var source: Source):Parcelable{

}

@Parcelize
data class Source(var id:String,var name:String):Parcelable

data class NewsResponse(var status:String, var totalResults:Int, var articles:List<Article>)
