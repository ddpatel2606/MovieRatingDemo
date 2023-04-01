package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models


import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.*
import com.google.gson.annotations.SerializedName

data class MovieDetailModel(
    @SerializedName("credits")
    val credits: CreditsModel,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("videos")
    val videos: VideoListModel,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)