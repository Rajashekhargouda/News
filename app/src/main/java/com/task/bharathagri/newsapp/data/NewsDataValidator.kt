package com.task.bharathagri.newsapp.data

import com.task.bharathagri.newsapp.model.*
import retrofit2.Response

class NewsDataValidator {

    fun validateNewsResponse(result: Response<NewsResponse>,dataMapper: DataMapper): UiResponseModel<List<INewsUiModel>> {
        val uiResponseModel:UiResponseModel<List<INewsUiModel>> = UiResponseModel()
        if (result.isSuccessful && result.body()!=null){
            if (result.body()?.articles?.isNotEmpty() == true){
                val newsModel = dataMapper.mapToNewsUiModel(result.body()?.articles!!)
                uiResponseModel.responseState = ResponseState.SUCCESS
                uiResponseModel.result = newsModel
            }else{
                uiResponseModel.responseState = ResponseState.NODATA
            }
        }else{
            uiResponseModel.responseState = ResponseState.ERROR
            uiResponseModel.errorCode = result?.code()
        }
        return uiResponseModel
    }
}