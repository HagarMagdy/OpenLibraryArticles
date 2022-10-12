package com.example.openlibraryarticles.repository

import android.util.Log
import com.example.openlibraryarticles.Config
import com.example.openlibraryarticles.api.OpenLibraryRetrofit
import com.example.openlibraryarticles.model.ArticleMapper
import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.model.response.ApiResponse
import com.example.openlibraryarticles.utills.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject
constructor(
    private val retrofit: OpenLibraryRetrofit,
    private val networkMapper: ArticleMapper
) : MainRepo {
    override suspend fun getArticles(queryMap: HashMap<String, String>): Flow<DataState<List<Article>>> =
        flow {
            emit(DataState.Loading)
            delay(1000)
            try {
                val apiResponse: ApiResponse = retrofit.fetchArticles(queryMap)

                val articles = networkMapper.mapFromEntityList(apiResponse.articles)

                emit(DataState.Success(articles))

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
}