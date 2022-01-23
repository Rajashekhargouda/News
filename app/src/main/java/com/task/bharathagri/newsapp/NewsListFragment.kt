package com.task.bharathagri.newsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.bharathagri.newsapp.databinding.FragmentNewsListBinding
import com.task.bharathagri.newsapp.model.Article
import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.ResponseState

class NewsListFragment:Fragment(),NewsAdapter.INewsItemClickListener {
    var isLoading:Boolean = false
    var pageNum:Int = 1
    var newsViewModel:NewsViewModel? = null
    var newsList:MutableList<INewsUiModel>? = null
    val PAGE_MAX_SIZE = 5
    var newsAdapter:NewsAdapter? = null
    var binding:FragmentNewsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsListBinding.inflate(inflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        newsList = mutableListOf()
        newsAdapter = NewsAdapter(newsList!!,this)
        newsViewModel = ViewModelProviders.of(this,
            NewsViewModelFactory(NewsDataProvider.getNewsRepository()))
            .get(NewsViewModel::class.java)
        pageNum = 1
        initView()
        getNews(pageNum)
    }

    private fun initView(){
        val linearLayoutManager = LinearLayoutManager(this.context)
        binding!!.rvNewsList.layoutManager = linearLayoutManager
        binding!!.rvNewsList.adapter = newsAdapter!!
        binding!!.rvNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading && pageNum<=PAGE_MAX_SIZE){
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0){
                        pageNum += 1
                        Log.e("page","page num is "+pageNum)
                        loadMoreData(pageNum)
                    }
                }
            }
        })
    }

    private fun loadMoreData(page: Int){
        isLoading = true
        newsAdapter?.isFooterVisible = true
        newsAdapter?.notifyDataSetChanged()
        getNews(page)
    }

    private fun getNews(page:Int){
        newsViewModel?.getNews(page)?.observe(viewLifecycleOwner, {
            when(it.responseState){
                ResponseState.SUCCESS ->{
                    isLoading = false
                    Log.e("getNews","response size is "+it?.result?.size)
                    newsList?.addAll(it?.result!!)
                    Log.e("getNews","newsList size is "+newsList?.size)
                    newsAdapter?.isFooterVisible = false
                    newsAdapter?.notifyDataSetChanged()
                }
                ResponseState.LOADING ->{
                    isLoading = true

                }
                ResponseState.NODATA ->{
                    isLoading = false

                }
                ResponseState.ERROR ->{
                    isLoading = false

                }
            }
        })
    }

    override fun onNewsClicked(article: Article) {
       //navigate to news detail
       val navController =  Navigation.findNavController(this.requireActivity(),R.id.nav_host_fragment)
        navController.navigate(R.id.news_list_to_news_detail)

    }

}