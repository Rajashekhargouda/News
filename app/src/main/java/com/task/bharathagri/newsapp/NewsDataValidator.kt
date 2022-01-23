package com.task.bharathagri.newsapp

import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.NewsResponse
import com.task.bharathagri.newsapp.model.ResponseState
import com.task.bharathagri.newsapp.model.UiResponseModel
import retrofit2.Response

object NewsDataValidator {

    fun validateNewsResponse(result: Response<NewsResponse>,
                             uiResponseModel: UiResponseModel<List<INewsUiModel>>): UiResponseModel<List<INewsUiModel>> {
        if (result.isSuccessful && result.body()!=null){
            if (result.body()?.articles?.isNotEmpty() == true){
                val newsModel = DataMapper.mapToNewsUiModel(result.body()?.articles!!)
                uiResponseModel.responseState = ResponseState.SUCCESS
                uiResponseModel.result = newsModel
            }else{
                uiResponseModel.responseState = ResponseState.NODATA
            }
        }else{
            uiResponseModel.responseState = ResponseState.ERROR
            uiResponseModel.errorMessage = result.errorBody()?.toString()
        }
        return uiResponseModel
    }
}