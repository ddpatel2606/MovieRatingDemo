package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.api_interface

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.MovieDetailModel
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.MovieListModel
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.PersonDetailModel
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.VideoListModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {

    @GET("movie/{list_id}")
    suspend fun getMovieList(
        @Path("list_id") listId: String,
        @Query("page") page: Int,
        @Query("region") region: String?
    ): MovieListModel

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(): MovieListModel

    @GET("movie/{movie_id}/videos")
    suspend fun getTrendingMovieTrailers(@Path("movie_id") movieId: Int): VideoListModel

    @GET("search/movie")
    suspend fun getMovieSearchResults(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieListModel

    @GET("movie/{movie_id}?&append_to_response=credits,videos")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDetailModel

    @GET("person/{person_id}?&append_to_response=movie_credits,tv_credits")
    suspend fun getPersonDetails(@Path("person_id") personId: Int): PersonDetailModel
}