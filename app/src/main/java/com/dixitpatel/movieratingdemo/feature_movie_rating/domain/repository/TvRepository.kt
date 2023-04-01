package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository

import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource

interface TvRepository {
    suspend fun getTvList(listId: String, page: Int): Resource<TvList>
    suspend fun getTrendingTvs(): Resource<TvList>
    suspend fun getTrendingTvTrailers(tvId: Int): Resource<VideoList>
    suspend fun getTvSearchResults(query: String, page: Int): Resource<TvList>
    suspend fun getTvDetails(tvId: Int): Resource<TvDetail>
    suspend fun getFavoriteTvs(): List<FavoriteTv>
    suspend fun tvExists(tvId: Int): Boolean
    suspend fun insertTv(tv: FavoriteTv)
    suspend fun deleteTv(tv: FavoriteTv)
}