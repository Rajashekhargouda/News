package com.task.bharathagri.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.bharathagri.newsapp.SingleEvent
import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.ResponseState
import com.task.bharathagri.newsapp.model.UiResponseModel
import com.task.bharathagri.newsapp.newsrepository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private var newsRepository: NewsRepository):ViewModel() {
    var newsLiveData = SingleEvent<UiResponseModel<List<INewsUiModel>>>()
    var newsData:MutableList<INewsUiModel> = mutableListOf()
    var pageNum = 1

    fun getNews(page:Int,isNetworkAvailable:Boolean):LiveData<UiResponseModel<List<INewsUiModel>>>{
        pageNum = page
        if (isNetworkAvailable){
            var uiResponseModel = UiResponseModel<List<INewsUiModel>>()
            uiResponseModel.responseState = ResponseState.LOADING
            newsLiveData.value = uiResponseModel
            viewModelScope.launch {
                uiResponseModel = newsRepository.getNews(page)
                if (uiResponseModel.responseState == ResponseState.SUCCESS){
                    newsData.addAll(uiResponseModel.result!!)
                }
                newsLiveData.postValue(uiResponseModel)
            }
            return newsLiveData
        }else{
            val uiResponseModel = UiResponseModel<List<INewsUiModel>>()
            uiResponseModel.responseState = ResponseState.ERROR
            newsLiveData.value = uiResponseModel
            return newsLiveData
        }

    }
}