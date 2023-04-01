package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models


import com.google.gson.annotations.SerializedName

data class TvListModel(
    @SerializedName("results")
    val results: List<TvModel>,
    @SerializedName("total_results")
    val totalResults: Int
)

data class TvModel(
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double
)