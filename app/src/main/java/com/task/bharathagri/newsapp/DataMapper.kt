package com.task.bharathagri.newsapp

import com.task.bharathagri.newsapp.model.Article
import com.task.bharathagri.newsapp.model.ImageItemModel
import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.NewsItemModel

object DataMapper {

    fun mapToNewsUiModel(articlesList:List<Article>):List<INewsUiModel>{
        //staticImageUrl
        val imageUrl = "https://jp.techcrunch.com/wp-content/uploads/2022/01/Twitter-NFTs.jpeg?w=990"
        val newsItemList = mutableListOf<INewsUiModel>()
        articlesList.forEachIndexed { index, article ->
            if (index>0&&index%6==0 && index!=articlesList.size-1){
                //add static image item
                newsItemList.add(ImageItemModel(imageUrl))
            }
            //add news item
            newsItemList.add(NewsItemModel(article))
        }
        return newsItemList
    }
}