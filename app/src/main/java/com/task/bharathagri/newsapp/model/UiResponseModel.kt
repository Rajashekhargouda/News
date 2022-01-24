package com.task.bharathagri.newsapp.model

import kotlin.Error

class UiResponseModel<T> {
    var result:T? = null
    var responseState:ResponseState = ResponseState.UNINITIALIZED
    var errorCode:Int? = null
}

enum class ResponseState{
    LOADING, SUCCESS, ERROR, NODATA,UNINITIALIZED
}
