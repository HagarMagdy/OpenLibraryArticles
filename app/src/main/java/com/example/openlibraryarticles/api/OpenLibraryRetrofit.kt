package com.example.openlibraryarticles.api

import com.example.openlibraryarticles.model.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface OpenLibraryRetrofit {
    /**
     * This interface represents the endpoint apis of articles list
     */
    @GET("all-sections/30.json")
    suspend fun fetchArticles(@QueryMap queryMap: HashMap<String, String>): ApiResponse

}