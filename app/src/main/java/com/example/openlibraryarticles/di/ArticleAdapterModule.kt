package com.example.openlibraryarticles.di

import com.example.openlibraryarticles.model.domain.Article
import com.example.openlibraryarticles.ui.adapter.ArticlesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.Channel


/**
 * This component is for providing the Articles adapter and it's stuff
 */
@Module
@InstallIn(ActivityComponent::class)
object ArticleAdapterModule {
    @Provides
    @ActivityScoped
    fun provideArticlesList(): ArrayList<Article>{
        return ArrayList()
    }

    @Provides
    @ActivityScoped
    fun provideActionChannel(): Channel<Article> {
        return Channel()
    }

    @Provides
    @ActivityScoped
    fun provideArticlesAdapter(
        arrayList: ArrayList<Article>,
        actionChannel: Channel<Article>
    ): ArticlesAdapter{
        return ArticlesAdapter(arrayList, actionChannel)
    }
}