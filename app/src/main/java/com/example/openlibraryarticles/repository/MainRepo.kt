package com.example.openlibraryarticles.repository

import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.utills.DataState
import kotlinx.coroutines.flow.Flow

interface MainRepo {
    suspend fun getArticles(queryMap: HashMap<String, String>): Flow<DataState<List<Article>>>
}