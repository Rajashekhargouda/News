package com.task.bharathagri.newsapp.model

interface INewsUiModel
data class ImageItemModel(var imageUrl:String):INewsUiModel
data class NewsItemModel(var article: Article):INewsUiModel