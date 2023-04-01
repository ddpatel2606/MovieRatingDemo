package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model

data class TvDetail(
    val createdBy: List<Creator>,
    val credits: Credits,
    val episodeRunTime: List<Int>,
    val firstAirDate: String?,
    val homepage: String?,
    val id: Int,
    val inProduction: Boolean,
    val lastAirDate: String?,
    val name: String,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originalName: String,
    val overview: String?,
    val posterPath: String?,
    val seasons: List<Season>,
    val status: String,
    val videos: VideoList,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun getAiringDates() = if (!firstAirDate.isNullOrEmpty()) {
        if (status != "Ended") firstAirDate.take(4)
        else "${firstAirDate.take(4)} - ${lastAirDate?.take(4)}"
    } else ""

    fun trimCreatorList() = createdBy.joinToString { it.name }

    companion object {
        val empty = TvDetail(
            createdBy = emptyList(),
            credits = Credits.empty,
            episodeRunTime = emptyList(),
            firstAirDate = null,
            homepage = null,
            id = 0,
            inProduction = false,
            lastAirDate = null,
            name = "",
            numberOfEpisodes = 0,
            numberOfSeasons = 0,
            originalName = "",
            overview = null,
            posterPath = null,
            seasons = emptyList(),
            status = "",
            videos = VideoList.empty,
            voteAverage = 0.0,
            voteCount = 0
        )
    }
}

data class Creator(
    val name: String
)

data class Season(
    val airDate: String?,
    val episodeCount: Int,
    val name: String,
    val posterPath: String?,
    val seasonNumber: Int
)