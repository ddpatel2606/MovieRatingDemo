package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models

import com.google.gson.annotations.SerializedName

data class VideoListModel(
    @SerializedName("results")
    val results: List<VideoModel>
)

data class VideoModel(
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("type")
    val type: String
)