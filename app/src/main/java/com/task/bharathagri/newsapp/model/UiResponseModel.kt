package com.task.bharathagri.newsapp.model

class UiResponseModel<T> {
    var result:T? = null
    var responseState:ResponseState = ResponseState.UNINITIALIZED
    var errorMessage:String? = null
}

enum class ResponseState{
    LOADING, SUCCESS, ERROR, NODATA,UNINITIALIZED
}
