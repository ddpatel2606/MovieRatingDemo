package com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository


import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.dao.MovieDao
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.mapper.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.api_interface.MovieApiInterface
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SafeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApiInterface,
    private val safeApiCall: SafeApiCall,
    private val dao: MovieDao
) : MovieRepository {

    override suspend fun getMovieList(
        listId: String,
        page: Int,
        region: String?
    ): Resource<MovieList> = safeApiCall.execute {
        api.getMovieList(listId, page, region).toMovieList()
    }

    override suspend fun getTrendingMovies(): Resource<MovieList> = safeApiCall.execute {
        api.getTrendingMovies().toMovieList()
    }

    override suspend fun getTrendingMovieTrailers(movieId: Int): Resource<VideoList> =
        safeApiCall.execute {
            api.getTrendingMovieTrailers(movieId).toVideoList()
        }

    override suspend fun getMovieSearchResults(query: String, page: Int): Resource<MovieList> =
        safeApiCall.execute {
            api.getMovieSearchResults(query, page).toMovieList()
        }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetail> =
        safeApiCall.execute {
            api.getMovieDetails(movieId).toMovieDetail()
        }

    override suspend fun getPersonDetails(personId: Int): Resource<PersonDetail> =
        safeApiCall.execute {
            api.getPersonDetails(personId).toPersonDetail()
        }

    override suspend fun getFavoriteMovies(): List<FavoriteMovie> =
        dao.getAllMovies().map { it.toFavoriteMovie() }

    override suspend fun movieExists(movieId: Int): Boolean = dao.movieExists(movieId)

    override suspend fun insertMovie(movie: FavoriteMovie) {
        dao.insertMovie(movie.toFavoriteMovieEntity())
    }

    override suspend fun deleteMovie(movie: FavoriteMovie) {
        dao.deleteMovie(movie.toFavoriteMovieEntity())
    }
}