package com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.dao.TvDao
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.mapper.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.api_interface.TvApiInterface
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SafeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvRepositoryImpl @Inject constructor(
    private val api: TvApiInterface,
    private val safeApiCall: SafeApiCall,
    private val dao: TvDao
) : TvRepository {

    override suspend fun getTvList(listId: String, page: Int): Resource<TvList> =
        safeApiCall.execute {
            api.getTvList(listId, page).toTvList()
        }

    override suspend fun getTrendingTvs(): Resource<TvList> = safeApiCall.execute {
        api.getTrendingTvs().toTvList()
    }

    override suspend fun getTrendingTvTrailers(tvId: Int): Resource<VideoList> =
        safeApiCall.execute {
            api.getTrendingTvTrailers(tvId).toVideoList()
        }

    override suspend fun getTvSearchResults(query: String, page: Int): Resource<TvList> =
        safeApiCall.execute {
            api.getTvSearchResults(query, page).toTvList()
        }

    override suspend fun getTvDetails(tvId: Int): Resource<TvDetail> = safeApiCall.execute {
        api.getTvDetails(tvId).toTvDetail()
    }

    override suspend fun getFavoriteTvs(): List<FavoriteTv> =
        dao.getAllTvs().map { it.toFavoriteTv() }

    override suspend fun tvExists(tvId: Int): Boolean = dao.tvExists(tvId)

    override suspend fun insertTv(tv: FavoriteTv) = dao.insertTv(tv.toFavoriteTvEntity())

    override suspend fun deleteTv(tv: FavoriteTv) = dao.deleteTv(tv.toFavoriteTvEntity())
}