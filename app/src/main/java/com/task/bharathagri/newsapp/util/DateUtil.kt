package com.task.bharathagri.newsapp.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun formatDate(dateInString:String):String{
        var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.getDefault())
        val date = dateFormat.parse(dateInString)
        dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm",Locale.getDefault())
        return dateFormat.format(date!!)
    }
}