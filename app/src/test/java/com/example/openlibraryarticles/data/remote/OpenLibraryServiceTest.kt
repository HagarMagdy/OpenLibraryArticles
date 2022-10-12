package com.example.openlibraryarticles.data.remote

import com.example.openlibraryarticles.Config
import com.example.openlibraryarticles.api.OpenLibraryRetrofit
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenLibraryServiceTest {
    @get:Rule
    val mockWebServer = MockWebServer()

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

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockWebServer.enqueue(
            MockResponse()
                .setBody(dummyResponseJson)
                .setResponseCode(200)
        )
    }

    @Test
    fun `get articles load all the articles from the json file as expected`() {

        val response = runBlocking {
            val map = hashMapOf(Config.apiKey to Config.apiKeyValue)
            openLibraryService.fetchArticles(map)
        }
        Assert.assertEquals(response.articles.size, 2)
    }


}