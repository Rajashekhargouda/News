package com.task.bharathagri.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.TestObserver
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.task.bharathagri.newsapp.model.INewsUiModel
import com.task.bharathagri.newsapp.model.ResponseState
import com.task.bharathagri.newsapp.model.UiResponseModel
import com.task.bharathagri.newsapp.newsrepository.NewsRepository
import com.task.bharathagri.newsapp.viewmodel.NewsViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsViewModelTest {


    @get:Rule
    public val instantTaskExecutorRule:InstantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var newsObserver:TestObserver<UiResponseModel<List<INewsUiModel>>>
    var newsRepository:NewsRepository = mock()
    var viewModel: NewsViewModel = NewsViewModel(newsRepository)
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
    fun `show error state when news can not be fetched`()= runBlocking {
        val uiResponseModel:UiResponseModel<List<INewsUiModel>> = UiResponseModel()
        uiResponseModel.responseState = ResponseState.ERROR
        whenever(newsRepository.getNews(1)).thenReturn(uiResponseModel)
        newsObserver = TestObserver.test(viewModel.getNews(1,true))
        val errorState = uiResponseModel.responseState
        assertEquals(newsObserver.value().responseState,errorState)
    }

    @Test
    fun `show error state when internet is not available`()= runBlocking {
        val uiResponseModel:UiResponseModel<List<INewsUiModel>> = UiResponseModel()
        uiResponseModel.responseState = ResponseState.ERROR
        newsObserver = TestObserver.test(viewModel.getNews(1,false))
        assertEquals(ResponseState.ERROR,newsObserver.value().responseState)
    }

    @Test
    fun `show loading while news is being fetched`() =  runBlocking {
        val uiResponseModel:UiResponseModel<List<INewsUiModel>> = UiResponseModel()
        uiResponseModel.responseState = ResponseState.LOADING
        whenever(newsRepository.getNews(1)).thenReturn(uiResponseModel)
        newsObserver = TestObserver.test(viewModel.getNews(1,true))
        assertEquals(ResponseState.LOADING,newsObserver.value().responseState)
    }

    @Test
    fun `show success when internet is available`() =  runBlocking {
        val uiResponseModel:UiResponseModel<List<INewsUiModel>> = UiResponseModel()
        viewModel.newsData = mock()
        uiResponseModel.responseState = ResponseState.SUCCESS
        uiResponseModel.result = mock()
        whenever(newsRepository.getNews(1)).thenReturn(uiResponseModel)
        newsObserver = TestObserver.test(viewModel.getNews(1,true))
        assertEquals(ResponseState.SUCCESS,newsObserver.value().responseState)
    }

}

