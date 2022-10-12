package com.example.openlibraryarticles.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.openlibraryarticles.api.OpenLibraryRetrofit
import com.example.openlibraryarticles.model.ArticleMapper
import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.model.response.ApiResponse
import com.example.openlibraryarticles.repository.MainRepo
import com.example.openlibraryarticles.repository.MainRepository
import com.example.openlibraryarticles.ui.viewmodels.MainViewModel
import com.example.openlibraryarticles.utills.DataState
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MainViewModelIntegrationTest {

    @get:Rule
    val mockWebServer = MockWebServer()

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var stateObserver: Observer<DataState<List<Article>>>

    private lateinit var viewModel: MainViewModel

    lateinit var repository: MainRepo

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val dummyResponseJson = ClassLoader.getSystemResource("articles.json").readText()

    private val openLibraryService by lazy {
        retrofit.create(OpenLibraryRetrofit::class.java)
    }

    private val networkMapper by lazy {
        ArticleMapper()
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockWebServer.enqueue(
            MockResponse()
                .setBody(dummyResponseJson)
                .setResponseCode(200)
        )
        repository = MainRepository(openLibraryService, networkMapper)
        viewModel = MainViewModel(repository)
        viewModel.dataState.observeForever(stateObserver)
    }

    @Test
    fun `get articles change view state to Success if there is articles`() = runBlocking {
        val result = repository.getArticles(hashMapOf())
        var finalDataState: DataState<List<Article>>? = null
        result.collect { finalDataState = it }
        delay(100)
        val response = Gson().fromJson(dummyResponseJson, ApiResponse::class.java)
        assertThat(finalDataState).isEqualTo(DataState.Success(ArticleMapper().mapFromEntityList(response.articles)))
    }

}