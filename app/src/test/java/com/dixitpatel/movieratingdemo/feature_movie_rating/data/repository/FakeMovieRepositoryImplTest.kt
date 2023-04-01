package com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository

import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource

class FakeMovieRepositoryImplTest : MovieRepository {

    override suspend fun getMovieList(
        listId: String,
        page: Int,
        region: String?
    ): Resource<MovieList> {
        return Resource.Error("Fake data")
    }

    override suspend fun getTrendingMovies(): Resource<MovieList> {
        return Resource.Error("Fake data")
    }

    override suspend fun getTrendingMovieTrailers(movieId: Int): Resource<VideoList> {
        return Resource.Error("Fake data")
    }

    override suspend fun getMovieSearchResults(query: String, page: Int): Resource<MovieList> {
        return Resource.Error("Fake data")
    }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail> {
        return Resource.Error("Fake data")
    }

    override suspend fun getPersonDetails(personId: Int): Resource<PersonDetail> {
        return Resource.Error("Fake data")
    }

    override suspend fun getFavoriteMovies(): List<FavoriteMovie> {
        return listOf()
    }

    override suspend fun movieExists(movieId: Int): Boolean {
        return false
    }

    override suspend fun insertMovie(movie: FavoriteMovie) {
    }

    override suspend fun deleteMovie(movie: FavoriteMovie) {
    }

}