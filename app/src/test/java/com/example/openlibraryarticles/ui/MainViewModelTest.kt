package com.example.openlibraryarticles.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.openlibraryarticles.mock.MockResponseStatus
import com.example.openlibraryarticles.mock.MockedMainRepository
import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.repository.MainRepo
import com.example.openlibraryarticles.ui.viewmodels.MainViewModel
import com.example.openlibraryarticles.utills.DataState
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainViewModelTest {

    @get:Rule
    var testRule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: MainRepo

    @MockK
    lateinit var dataState: Observer<DataState<List<Article>>>

    private lateinit var viewModel: MainViewModel

    private val dummyException = "Unknown error"

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = MockedMainRepository()
        viewModel = MainViewModel(repository)
        viewModel.dataState.observeForever(dataState)
    }

    @Test
    fun `load articles change view state to Success if there is articles`() = runBlocking {
        (repository as MockedMainRepository).currentMockedResponse = MockResponseStatus.Success
        val result = repository.getArticles(hashMapOf())
        result.collect {
            if (it is DataState.Success) {
                assertThat(it).isEqualTo(DataState.Success((repository as MockedMainRepository).dummyResponse))
            } else {
                assertThat(it).isEqualTo(DataState.Loading)
            }
        }
    }

    @Test
    fun `load articles change view state to EMPTY if there is no articles`() = runBlocking {
        (repository as MockedMainRepository).currentMockedResponse = MockResponseStatus.EMPTY
        val result = repository.getArticles(hashMapOf())
        result.collect {
            if (it is DataState.Success) {
                assertThat(it.data.size).isEqualTo(0)
            } else {
                assertThat(it).isEqualTo(DataState.Loading)
            }
        }
    }

    @Test
    fun `load articles change view state to ERROR if repository throws an error`() = runBlocking {
        (repository as MockedMainRepository).currentMockedResponse = MockResponseStatus.Error
        val result = repository.getArticles(hashMapOf())
        result.collect {
            if (it is DataState.Error) {
                assertThat(it.exception.message).isEqualTo(dummyException)
            } else {
                assertThat(it).isEqualTo(DataState.Loading)
            }
        }
    }

}