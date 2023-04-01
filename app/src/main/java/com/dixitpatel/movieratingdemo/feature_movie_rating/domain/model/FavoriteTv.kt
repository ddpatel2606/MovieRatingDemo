package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model

data class FavoriteTv(
    val id: Int,
    val episodeRunTime: Int,
    val firstAirDate: String?,
    val name: String,
    val posterPath: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val date: Long
)