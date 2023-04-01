package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.ILLEGAL_ARGUMENT_MEDIA_TYPE
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import javax.inject.Inject

class CheckFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(
        mediaType: SelectedMediaType,
        id: Int
    ): Boolean = when (mediaType) {
        SelectedMediaType.MOVIE -> movieRepository.movieExists(id)
        SelectedMediaType.TV -> tvRepository.tvExists(id)
        else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
    }
}