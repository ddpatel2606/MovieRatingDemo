package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.ILLEGAL_ARGUMENT_MEDIA_TYPE
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    operator fun invoke(
        mediaType: SelectedMediaType,
        query: String,
        page: Int
    ): Flow<Resource<Any>> = flow {
        emit(
            when (mediaType) {
                SelectedMediaType.MOVIE -> movieRepository.getMovieSearchResults(query, page)
                SelectedMediaType.TV -> tvRepository.getTvSearchResults(query, page)
                else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
            }
        )
    }
}