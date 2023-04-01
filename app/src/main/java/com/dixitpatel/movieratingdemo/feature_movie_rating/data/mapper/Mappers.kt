package com.dixitpatel.movieratingdemo.feature_movie_rating.data.mapper

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.entities.FavoriteMovieEntity
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.entities.FavoriteTvEntity
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*

fun FavoriteMovie.toFavoriteMovieEntity() = FavoriteMovieEntity(id, posterPath, releaseDate, runtime, title, voteAverage, voteCount, date)

fun FavoriteMovieEntity.toFavoriteMovie() = FavoriteMovie(id, posterPath, releaseDate, runtime, title, voteAverage, voteCount, date)

fun FavoriteTv.toFavoriteTvEntity() = FavoriteTvEntity(id, episodeRunTime, firstAirDate, name, posterPath, voteAverage, voteCount, date)

fun FavoriteTvEntity.toFavoriteTv() = FavoriteTv(id, episodeRunTime, firstAirDate, name, posterPath, voteAverage, voteCount, date)

fun CreatorModel.toCreator() = Creator(name)

fun CreditsModel.toCredits() = Credits(cast.map { it.toPerson() }, crew.map { it.toPerson() }, guestStars?.map { it.toPerson() })

fun MovieModel.toMovie() = Movie(id, overview, posterPath, releaseDate, title, voteAverage)

fun MovieDetailModel.toMovieDetail() = MovieDetail(
    credits.toCredits(),
    homepage,
    id,
    originalTitle,
    overview,
    posterPath,
    releaseDate,
    runtime,
    status,
    title,
    videos.toVideoList(),
    voteAverage,
    voteCount
)

fun MovieListModel.toMovieList() = MovieList(results.map { it.toMovie() }, totalResults)

fun PersonModel.toPerson() = Person(character, department, id, job, knownForDepartment, name, profilePath)

fun SeasonModel.toSeason() = Season(airDate, episodeCount, name, posterPath, seasonNumber)

fun TvModel.toTv() = Tv(firstAirDate, id, name, overview, posterPath, voteAverage)

fun TvDetailModel.toTvDetail() = TvDetail(
    createdBy.map { it.toCreator() },
    credits.toCredits(),
    episodeRunTime,
    firstAirDate,
    homepage,
    id,
    inProduction,
    lastAirDate,
    name,
    numberOfEpisodes,
    numberOfSeasons,
    originalName,
    overview,
    posterPath,
    seasons.map { it.toSeason() },
    status,
    videos.toVideoList(),
    voteAverage,
    voteCount
)

fun PersonDetailModel.toPersonDetail() = PersonDetail(
    biography,
    birthday,
    deathday,
    gender,
    homepage,
    id,
    knownForDepartment,
    name,
    placeOfBirth,
    profilePath
)

fun TvListModel.toTvList() = TvList(results.map { it.toTv() }, totalResults)

fun VideoModel.toVideo() = Video(key, name, publishedAt, site, type)

fun VideoListModel.toVideoList() = VideoList(results.map { it.toVideo() })