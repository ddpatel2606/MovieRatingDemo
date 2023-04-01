package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases

import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    operator fun invoke(
        mediaType: SelectedMediaType,
        id: Int
    ): Flow<Resource<Any>> = flow {
        emit(
            when (mediaType) {
                SelectedMediaType.MOVIE -> movieRepository.getMovieDetails(id)
                SelectedMediaType.TV -> tvRepository.getTvDetails(id)
                SelectedMediaType.PERSON -> movieRepository.getPersonDetails(id)
            }
        )
    }
}