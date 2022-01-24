package com.task.bharathagri.newsapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NEWS_VIEW_TYPE -> {
                val newsBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                NewsViewHolder(newsBinding,iNewsItemClickListener)
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
            holder.bind(data as NewsItemModel)
        }else if (holder is ImageViewHolder){
            holder.bind(data as ImageItemModel)
        }
    }

    override fun getItemCount(): Int {
        return newsUiModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (newsUiModelList[position] is NewsItemModel) {
            NEWS_VIEW_TYPE
        } else {
            IMAGE_VIEW_TYPE
        }
    }


    class NewsViewHolder(var newsBinding: ItemNewsBinding, var iNewsItemClickListener: INewsItemClickListener) : RecyclerView.ViewHolder(newsBinding.root) {
        fun bind(newsItemModel: NewsItemModel){
            newsBinding.article = newsItemModel.article
            newsBinding.dateUtil = DateUtil
            newsBinding.clickListener = iNewsItemClickListener
            newsBinding.executePendingBindings()
        }
    }

    class ImageViewHolder(var imageBinding:ItemImageBinding):RecyclerView.ViewHolder(imageBinding.root){
        fun bind(imageItemModel:ImageItemModel){
            imageBinding.imageItem = imageItemModel
            imageBinding.executePendingBindings()
        }
    }

    interface INewsItemClickListener{
        fun onNewsClicked(article: Article)
    }
}
