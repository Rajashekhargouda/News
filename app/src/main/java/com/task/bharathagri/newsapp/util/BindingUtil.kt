package com.task.bharathagri.newsapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingUtil {
    companion object{
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView,imageUrl:String){
            Glide.with(imageView.context).load(imageUrl).into(imageView)
        }
    }


}