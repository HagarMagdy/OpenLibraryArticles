package com.example.openlibraryarticles.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("status") @Expose var status: String? = null,
    @SerializedName("copyright") @Expose var copyright: String? = null,
    @SerializedName("results") @Expose var articles: ArrayList<NetworkArticle> = arrayListOf(),
    @SerializedName("num_results") @Expose var numResults: Int? = null)
