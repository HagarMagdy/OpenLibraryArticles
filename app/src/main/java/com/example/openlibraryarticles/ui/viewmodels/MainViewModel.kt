package com.example.openlibraryarticles.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openlibraryarticles.Config
import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.repository.MainRepo
import com.example.openlibraryarticles.utills.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepo
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Article>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Article>>>
        get() = _dataState

    /**
     * function to fetch articles
     */
    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetArticleEvent -> {

                    mainRepository.getArticles(
                        hashMapOf(
                            Config.apiKey to Config.apiKeyValue
                        )
                    )
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)

                }
                is MainStateEvent.RefreshScreenEvent ->{
                    refreshScreen()
                }
            }
        }
    }
    /**
     * function to refresh recycler view
     */
    private fun refreshScreen() {
        _dataState.value = DataState.CLEAR
        setStateEvent(MainStateEvent.GetArticleEvent)
    }
}

sealed class MainStateEvent {
    object GetArticleEvent : MainStateEvent()
    object RefreshScreenEvent : MainStateEvent()

}