package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository

import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource

interface MovieRepository {
    suspend fun getMovieList(listId: String, page: Int, region: String?): Resource<MovieList>
    suspend fun getTrendingMovies(): Resource<MovieList>
    suspend fun getTrendingMovieTrailers(movieId: Int): Resource<VideoList>
    suspend fun getMovieSearchResults(query: String, page: Int): Resource<MovieList>
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail>
    suspend fun getPersonDetails(personId: Int): Resource<PersonDetail>
    suspend fun getFavoriteMovies(): List<FavoriteMovie>
    suspend fun movieExists(movieId: Int): Boolean
    suspend fun insertMovie(movie: FavoriteMovie)
    suspend fun deleteMovie(movie: FavoriteMovie)
}