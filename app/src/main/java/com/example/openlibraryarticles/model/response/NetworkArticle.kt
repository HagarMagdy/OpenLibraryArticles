package com.example.openlibraryarticles.model.response

import com.example.openlibraryarticles.model.domain.MediaItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkArticle
    (
    @SerializedName("uri") @Expose var uri: String? = null,
    @SerializedName("url")@Expose var url: String? = null,
    @SerializedName("id") @Expose var id: Long? = null,
    @SerializedName("asset_id")@Expose var assetId: Long? = null,
    @SerializedName("source")@Expose var source: String? = null,
    @SerializedName("published_date")@Expose var publishedDate: String? = null,
    @SerializedName("updated")@Expose var updated: String? = null,
    @SerializedName("section")@Expose var section: String? = null,
    @SerializedName("subsection")@Expose var subsection: String? = null,
    @SerializedName("nytdsection")@Expose var nytdsection: String? = null,
    @SerializedName("adx_keywords")@Expose var adxKeywords: String? = null,
    @SerializedName("column")@Expose var column: String? = null,
    @SerializedName("byline")@Expose var byline: String? = null,
    @SerializedName("type")@Expose var type: String? = null,
    @SerializedName("title")@Expose var title: String? = null,
    @SerializedName("abstract")@Expose var abstract: String? = null,
    @SerializedName("des_facet")@Expose var desFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("org_facet")@Expose var orgFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("per_facet")@Expose var perFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("geo_facet")@Expose var geoFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("media")@Expose var media: ArrayList<MediaItem> = arrayListOf(),
    @SerializedName("eta_id")@Expose var etaId: Int? = null
)
