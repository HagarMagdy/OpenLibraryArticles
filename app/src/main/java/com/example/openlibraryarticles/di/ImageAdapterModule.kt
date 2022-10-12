package com.example.openlibraryarticles.di

import com.example.openlibraryarticles.ui.adapter.ImageAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


/**
 * This component is for providing the Images adapter and it's stuff
 */
@Module
@InstallIn(ActivityComponent::class)
object ImageAdapterModule {

    @Provides
    @ActivityScoped
    fun provideImagesList(): ArrayList<String>{
        return ArrayList()
    }

    @Provides
    @ActivityScoped
    fun provideImagesAdapter(
        arrayList: ArrayList<String>): ImageAdapter {
        return ImageAdapter(arrayList)
    }
}