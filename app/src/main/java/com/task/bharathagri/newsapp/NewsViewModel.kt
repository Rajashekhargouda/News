package com.task.bharathagri.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.ResponseState
import com.task.bharathagri.newsapp.model.UiResponseModel
import com.task.bharathagri.newsapp.newsrepository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private var newsRepository: NewsRepository):ViewModel() {
    var newsLiveData = SingleEvent<UiResponseModel<List<INewsUiModel>>>()

    fun getNews(page:Int):LiveData<UiResponseModel<List<INewsUiModel>>>{
        var uiResponseModel = UiResponseModel<List<INewsUiModel>>()
        uiResponseModel.responseState = ResponseState.LOADING
        newsLiveData.value = uiResponseModel
        viewModelScope.launch {
            val result = newsRepository.getNews(page)
            uiResponseModel = NewsDataValidator.validateNewsResponse(result,uiResponseModel)
            newsLiveData.postValue(uiResponseModel)
        }
        return newsLiveData
    }
}