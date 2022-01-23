package com.task.bharathagri.newsapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.bharathagri.newsapp.databinding.ItemFooterBinding
import com.task.bharathagri.newsapp.databinding.ItemImageBinding
import com.task.bharathagri.newsapp.databinding.ItemNewsBinding
import com.task.bharathagri.newsapp.model.Article
import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.ImageItemModel
import com.task.bharathagri.newsapp.model.NewsItemModel
import com.task.bharathagri.newsapp.util.DateUtil

class NewsAdapter(var newsUiModelList:List<INewsUiModel>,var iNewsItemClickListener: INewsItemClickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var NEWS_VIEW_TYPE = 1
    val IMAGE_VIEW_TYPE = 2
    val FOOTER = 3
    var isFooterVisible:Boolean = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NEWS_VIEW_TYPE -> {
                val newsBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                NewsViewHolder(newsBinding)
            }
            FOOTER -> {
                val footerBinding = ItemFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return FooterViewHolder(footerBinding)
            }
            else -> {
                val imageBinding = ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                ImageViewHolder(imageBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = newsUiModelList[position]
        if (holder is NewsViewHolder){
            holder.bind(data as NewsItemModel,position)
        }else if (holder is ImageViewHolder){
            holder.bind(data as ImageItemModel,position)
        }

    }

    override fun getItemCount(): Int {
        return newsUiModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == newsUiModelList.size - 1 && isFooterVisible) {
            Log.e("viewType", "footer")
            FOOTER
        } else if (newsUiModelList[position] is NewsItemModel) {
            NEWS_VIEW_TYPE
        } else {
            IMAGE_VIEW_TYPE
        }
    }


    inner class NewsViewHolder(var newsBinding: ItemNewsBinding) : RecyclerView.ViewHolder(newsBinding.root) {
        fun bind(newsItemModel: NewsItemModel,position: Int){
            newsBinding.article = newsItemModel.article
            newsBinding.dateUtil = DateUtil
            newsBinding.clickListener = iNewsItemClickListener
            newsBinding.executePendingBindings()
        }
    }

    class ImageViewHolder(var imageBinding:ItemImageBinding):RecyclerView.ViewHolder(imageBinding.root){
        fun bind(imageItemModel:ImageItemModel,position: Int){
            imageBinding.imageItem = imageItemModel
            imageBinding.executePendingBindings()
        }

    }

    class FooterViewHolder(var itemFooterBinding: ItemFooterBinding):RecyclerView.ViewHolder(itemFooterBinding.root)

    interface INewsItemClickListener{
        fun onNewsClicked(article: Article)
    }
}
