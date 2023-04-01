package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model

data class MovieDetail(
    val credits: Credits,
    val homepage: String?,
    val id: Int,
    val originalTitle: String,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val status: String,
    val title: String,
    val videos: VideoList,
    val voteAverage: Double,
    val voteCount: Int
) {
    companion object {
        val empty = MovieDetail(
            credits = Credits.empty,
            homepage = null,
            id = 0,
            originalTitle = "",
            overview = null,
            posterPath = null,
            releaseDate = null,
            runtime = null,
            status = "",
            title = "",
            videos = VideoList.empty,
            voteAverage = 0.0,
            voteCount = 0
        )
    }
}