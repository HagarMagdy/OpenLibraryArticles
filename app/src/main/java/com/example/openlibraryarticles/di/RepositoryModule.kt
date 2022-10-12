package com.example.openlibraryarticles.di

import com.example.openlibraryarticles.api.OpenLibraryRetrofit
import com.example.openlibraryarticles.model.ArticleMapper
import com.example.openlibraryarticles.repository.MainRepo
import com.example.openlibraryarticles.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        retrofit: OpenLibraryRetrofit,
        networkMapper: ArticleMapper): MainRepo {
        return MainRepository(retrofit, networkMapper)
    }
}