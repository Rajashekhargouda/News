package com.task.bharathagri.newsapp.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.task.bharathagri.newsapp.R
import com.task.bharathagri.newsapp.databinding.ActivityNewsDetailBinding
import com.task.bharathagri.newsapp.model.Article
import com.task.bharathagri.newsapp.util.DateUtil

class NewsDetailActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val article = intent?.getParcelableExtra<Article>("article")
        val binding = DataBindingUtil.setContentView<ActivityNewsDetailBinding>(this,
            R.layout.activity_news_detail
        )
        setSupportActionBar(binding.tbNewsDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.article = article
        binding.dateUtil = DateUtil
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }


}