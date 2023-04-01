package com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.entities.FavoriteTvEntity
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.mapper.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.models.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import javax.inject.Inject

class TestTvRepository @Inject constructor() : TvRepository {

    val tvShow = mutableListOf<FavoriteTv>()

    override suspend fun getTvList(listId: String, page: Int): Resource<TvList> {
        return Resource.Success(TvListModel(listOf(TvModel("2023-01-15",100088,"The Last of Us","Twenty years after modern civilization has been destroyed",
        "/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",8.8),TvModel("2022-05-27",203057,"Melur Untuk Firdaus",
            "Melur Untuk Firdaus is a 2022 Malaysian",
            "/rVxQxsY3bQWTabHUi2Qr3aoOafk.jpg",4.7)), 2).toTvList())
    }

    override suspend fun getTrendingTvs(): Resource<TvList> {
        return Resource.Success(TvListModel(listOf(TvModel("2023-01-15",100088,"The Last of Us","Twenty years after modern civilization has been destroyed",
            "/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",8.8),TvModel("2022-05-27",203057,"Melur Untuk Firdaus",
            "Melur Untuk Firdaus is a 2022 Malaysian",
            "/rVxQxsY3bQWTabHUi2Qr3aoOafk.jpg",4.7)), 2).toTvList())
    }

    override suspend fun getTrendingTvTrailers(tvId: Int): Resource<VideoList> {
        return Resource.Success(VideoListModel(listOf()).toVideoList())
    }

    override suspend fun getTvSearchResults(query: String, page: Int): Resource<TvList> {
        return Resource.Success(TvListModel(listOf(), 2).toTvList())
    }

    override suspend fun getTvDetails(tvId: Int): Resource<TvDetail> {
        return Resource.Success(
            TvDetailModel(
                listOf(CreatorModel(
                    "Craig Mazin"),CreatorModel(
                    "Neil Druckmann")),
                CreditsModel(listOf(PersonModel("Joel Miller","Acting",1253360,"test",
                    "Acting","Pedro Pascal","/nms0d0ExYtiOke82oqr3vOb3smF.jpg")), listOf(
                    PersonModel("Ellie Williams","Acting",12533690,"test",
                        "Acting","Bella Ramsey","/4W2iNEfnFIB2TXa8kVgKzNXIwqb.jpg")
                    ), listOf(PersonModel("Ellie Williams","Acting",12533690,"test",
                    "Acting","Bella Ramsey","/4W2iNEfnFIB2TXa8kVgKzNXIwqb.jpg")
                )),
                listOf(59),
                "2023-01-15",
                "https://www.hbo.com/the-last-of-us",
                100088,
                true,
                "2023-03-12",
                "The Last of Us",
                24,
                1,
                "The Last of Us",
                "Twenty years after modern civilization has been destroyed",
                "/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",
                listOf(SeasonModel("2023-01-15",9,"Season 1","/aUQKIpZZ31KWbpdHMCmaV76u78T.jpg",1)),
                "Returning Series",
                VideoListModel(listOf(VideoModel("8SWhBsbxmpk",
                    "Opening Credits",
                    "2023-01-16T17:30:00.000Z","YouTube",
                    "Opening Credits"))),
                8.0,
                2934
            ).toTvDetail()
        )
    }

    override suspend fun getFavoriteTvs(): List<FavoriteTv> {
        return listOf(FavoriteTvEntity(1, 3, "2023-01-15", "The Last of Us", "/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg", 3.0, 3, 98L).toFavoriteTv())
    }

    override suspend fun tvExists(tvId: Int): Boolean {
        return tvShow.any { it.id == tvId }
    }

    override suspend fun insertTv(tv: FavoriteTv) {
        tvShow.add(tv)
    }

    override suspend fun deleteTv(tv: FavoriteTv) {
        tvShow.remove(tv)
    }
}
