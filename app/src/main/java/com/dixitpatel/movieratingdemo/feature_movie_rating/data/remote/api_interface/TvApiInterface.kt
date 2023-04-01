package com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.api_interface

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApiInterface {

    @GET("tv/{list_id}")
    suspend fun getTvList(@Path("list_id") listId: String, @Query("page") page: Int): TvListModel

    @GET("trending/tv/week")
    suspend fun getTrendingTvs(): TvListModel

    @GET("tv/{tv_id}/videos")
    suspend fun getTrendingTvTrailers(@Path("tv_id") tvId: Int): VideoListModel

    @GET("search/tv")
    suspend fun getTvSearchResults(
        @Query("query") query: String,
        @Query("page") page: Int
    ): TvListModel

    @GET("tv/{tv_id}?&append_to_response=credits,videos")
    suspend fun getTvDetails(@Path("tv_id") tvId: Int): TvDetailModel

}