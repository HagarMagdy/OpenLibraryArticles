package com.example.openlibraryarticles.mock

import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.repository.MainRepo
import com.example.openlibraryarticles.utills.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockedMainRepository : MainRepo {

    val dummyResponse = listOf(
        Article(title = "Article1"),
        Article(title = "Article2"),
        Article(title = "Article3"),
        Article(title = "Article4")
    )

    private val mockedSuccessFlow = flow {
        emit(DataState.Loading)
        delay(10)
        emit(DataState.Success(dummyResponse))
    }

    private val mockedEmptyListFlow = flow {
        emit(DataState.Loading)
        delay(10)
        emit(DataState.Success(listOf<Article>()))
    }

    private val mockedErrorFlow = flow {
        emit(DataState.Loading)
        delay(10)
        emit(DataState.Error(Exception("Unknown error")))
    }

    lateinit var currentMockedResponse: MockResponseStatus

    override suspend fun getArticles(queryMap: HashMap<String, String>): Flow<DataState<List<Article>>> =
        when (currentMockedResponse) {
            is MockResponseStatus.Success -> mockedSuccessFlow
            is MockResponseStatus.EMPTY -> mockedEmptyListFlow
            is MockResponseStatus.Error -> mockedErrorFlow
        }

}

sealed class MockResponseStatus {
    object Success : MockResponseStatus()
    object Error : MockResponseStatus()
    object EMPTY : MockResponseStatus()
}