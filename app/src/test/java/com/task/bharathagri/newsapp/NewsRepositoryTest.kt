package com.task.bharathagri.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.task.bharathagri.newsapp.data.DataMapper
import com.task.bharathagri.newsapp.data.NewsDataValidator
import com.task.bharathagri.newsapp.model.*
import com.task.bharathagri.newsapp.networking.NewsApiService
import com.task.bharathagri.newsapp.networking.RetrofitHelper
import com.task.bharathagri.newsapp.newsrepository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.*
import retrofit2.Response

@ExperimentalCoroutinesApi
class NewsRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule:InstantTaskExecutorRule = InstantTaskExecutorRule()

    var newsDataValidator: NewsDataValidator = mock()
    var dataMapper: DataMapper = mock()
    var newsApiService:NewsApiService = mock()
    var newsRepository :NewsRepository = NewsRepository(newsDataValidator,dataMapper,newsApiService)

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `return success when network is available`() = runBlockingTest{
        val uiResponseModel:UiResponseModel<List<INewsUiModel>> = UiResponseModel()
        uiResponseModel.responseState = ResponseState.SUCCESS
        uiResponseModel.result= getMockedNewsModel()

        val response : Response<NewsResponse> = Response.success(NewsResponse("ok", 10,getMockedArticles()))
        whenever(newsRepository.newsApiService.getNews(RetrofitHelper.getNewsUrl(1))).thenReturn(response)
        whenever(newsRepository.dataMapper.mapToNewsUiModel(response.body()?.articles!!)).thenReturn(getMockedNewsModel())
        whenever(newsRepository.newsDataValidator.validateNewsResponse(response,dataMapper)).thenReturn(uiResponseModel)

        val news = newsRepository.getNews(1)
        Assert.assertEquals(ResponseState.SUCCESS,news.responseState)
    }

    @Test
    fun `return error when network is not available`() = runBlockingTest {
        val uiResponseModel:UiResponseModel<List<INewsUiModel>> = UiResponseModel()
        uiResponseModel.responseState = ResponseState.ERROR
        uiResponseModel.result= null
        uiResponseModel.errorCode = 500
        var responseBody = mock<ResponseBody>()

        val response : Response<NewsResponse> = Response.error(500, responseBody)
        whenever(newsRepository.newsApiService.getNews(RetrofitHelper.getNewsUrl(1))).thenReturn(response)
        whenever(newsRepository.newsDataValidator.validateNewsResponse(response,dataMapper)).thenReturn(uiResponseModel)
        val news = newsRepository.getNews(1)
        Assert.assertEquals(ResponseState.ERROR,news.responseState)
    }

    private fun getMockedArticles():List<Article>{
        val articles : MutableList<Article> = mutableListOf()
        articles.add(Article("abcd","wer","des","url",
            "urlToImage","publishedAt","content",Source("id","name")))
        articles.add(Article("abcd","wer","des","url",
            "urlToImage","publishedAt","content",Source("id","name")))
        return articles
    }

    private fun getMockedNewsModel():List<INewsUiModel> {
        val newsList:MutableList<INewsUiModel> = mutableListOf()
        newsList.add(mock())
        newsList.add(mock())
        newsList.add(mock())
        return newsList
    }
}