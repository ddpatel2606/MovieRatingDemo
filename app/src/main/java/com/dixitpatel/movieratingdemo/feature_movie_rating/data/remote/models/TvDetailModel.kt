package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models


import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.*
import com.google.gson.annotations.SerializedName

data class TvDetailModel(
    @SerializedName("created_by")
    val createdBy: List<CreatorModel>,
    @SerializedName("credits")
    val credits: CreditsModel,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("in_production")
    val inProduction: Boolean,
    @SerializedName("last_air_date")
    val lastAirDate: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("seasons")
    val seasons: List<SeasonModel>,
    @SerializedName("status")
    val status: String,
    @SerializedName("videos")
    val videos: VideoListModel,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)

data class CreatorModel(
    @SerializedName("name")
    val name: String
)

data class SeasonModel(
    @SerializedName("air_date")
    val airDate: String?,
    @SerializedName("episode_count")
    val episodeCount: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("season_number")
    val seasonNumber: Int
)