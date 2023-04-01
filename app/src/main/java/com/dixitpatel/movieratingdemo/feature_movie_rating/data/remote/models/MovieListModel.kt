package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models


import com.google.gson.annotations.SerializedName

data class MovieListModel(
    @SerializedName("results")
    val results: List<MovieModel>,
    @SerializedName("total_results")
    val totalResults: Int
)

data class MovieModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double
)