package com.task.bharathagri.newsapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.bharathagri.newsapp.*
import com.task.bharathagri.newsapp.data.provider.NewsDataProvider
import com.task.bharathagri.newsapp.databinding.ActivityMainBinding
import com.task.bharathagri.newsapp.model.Article
import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.ResponseState
import com.task.bharathagri.newsapp.util.NetworkUtil
import com.task.bharathagri.newsapp.util.hide
import com.task.bharathagri.newsapp.util.show
import com.task.bharathagri.newsapp.view.adapter.NewsAdapter
import com.task.bharathagri.newsapp.viewmodel.NewsViewModel
import com.task.bharathagri.newsapp.viewmodel.NewsViewModelFactory

class NewsListActivity : AppCompatActivity(), NewsAdapter.INewsItemClickListener {
    var isLoading:Boolean = false
    var pageNum:Int = 1
    var newsViewModel: NewsViewModel? = null
    var newsList:MutableList<INewsUiModel>? = null
    val PAGE_MAX_SIZE = 5
    var newsAdapter: NewsAdapter? = null
    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding?.tbNewsList!!)
        newsList = mutableListOf()
        newsViewModel = ViewModelProviders.of(this,
                NewsViewModelFactory(
                    NewsDataProvider.getNewsRepository(
                        NewsDataProvider.getNewsDataValidator(),
                    NewsDataProvider.getDataMapper(), NewsDataProvider.getNewsApiService()
                    )
                )
        )
                .get(NewsViewModel::class.java)
        newsAdapter = NewsAdapter(newsList!!,this)
        initView()
        pageNum = newsViewModel?.pageNum?:1
        observeData()
        if (newsViewModel?.newsData?.isNotEmpty()==true){
            newsList?.addAll(newsViewModel?.newsData!!)
            newsAdapter?.notifyDataSetChanged()
        }else{
            getNews(pageNum)
        }
    }

    private fun initView(){
        val linearLayoutManager = LinearLayoutManager(this)
        binding!!.rvNewsList.layoutManager = linearLayoutManager
        binding!!.rvNewsList.adapter = newsAdapter!!
        binding!!.rvNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading && pageNum<PAGE_MAX_SIZE){
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0){
                        pageNum+=1
                        loadMoreData(pageNum)
                    }
                }
            }
        })
    }
    private fun observeData(){
        newsViewModel?.newsLiveData?.observe(this,Observer {
            when(it.responseState){
                ResponseState.SUCCESS ->{
                    binding!!.pbFooter.hide()
                    binding?.pbInitialLoader?.hide()
                    // binding?.tvError?.hide()
                    isLoading = false
                    newsList?.addAll(it?.result!!)
                    newsAdapter?.notifyDataSetChanged()
                }
                ResponseState.LOADING ->{
                    if (!isLoading)
                        binding!!.pbInitialLoader.show()
                    binding?.tvError?.hide()
                }
                ResponseState.NODATA ->{
                    if (pageNum>1){
                        pageNum-=1
                    }
                    isLoading = false
                    binding?.tvError?.text = getString(R.string.no_data_available)
                    binding!!.pbInitialLoader.hide()
                    binding!!.pbFooter.hide()
                    binding?.tvError?.show()
                }
                ResponseState.ERROR ->{
                    if (pageNum>1){
                        pageNum-=1
                    }
                    binding!!.pbInitialLoader.hide()
                    binding!!.pbFooter.hide()
                    if (isLoading){
                        isLoading = false
                        Toast.makeText(this,getString(R.string.something_went_wrong),Toast.LENGTH_SHORT).show()
                    }else{
                        binding?.tvError?.text = getString(R.string.something_went_wrong)
                        binding?.tvError?.show()
                    }
                }
            }
        })
    }



    private fun loadMoreData(page: Int){
        isLoading = true
        binding?.rvNewsList?.post {newsAdapter?.notifyDataSetChanged()  }
        binding?.pbFooter?.show()
        getNews(page)
    }

    private fun getNews(page:Int){
        newsViewModel?.getNews(page,NetworkUtil.isNetworkAvailable(this))
    }

    override fun onNewsClicked(article: Article) {
        //navigate to news detail
        val newsDetailIntent = Intent(this, NewsDetailActivity::class.java).apply {
            putExtra("article",article)
        }
        startActivity(newsDetailIntent)
    }
}
