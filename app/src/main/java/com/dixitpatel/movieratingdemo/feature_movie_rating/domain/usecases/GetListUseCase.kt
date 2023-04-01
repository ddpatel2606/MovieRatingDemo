package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.ILLEGAL_ARGUMENT_MEDIA_TYPE
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    operator fun invoke(
        mediaType: SelectedMediaType,
        listId: String?,
        page: Int? = null,
        region: String? = null
    ): Flow<Resource<Any>> = flow {
        emit(
            when (mediaType) {
                SelectedMediaType.MOVIE -> if (listId == null) movieRepository.getTrendingMovies() else movieRepository.getMovieList(
                    listId,
                    page!!,
                    region
                )
                SelectedMediaType.TV -> if (listId == null) tvRepository.getTrendingTvs() else tvRepository.getTvList(
                    listId,
                    page!!
                )
                else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
            }
        )
    }
}