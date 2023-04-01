package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.ILLEGAL_ARGUMENT_MEDIA_TYPE
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.FavoriteMovie
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.FavoriteTv
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(
        mediaType: SelectedMediaType,
        movie: FavoriteMovie? = null,
        tv: FavoriteTv? = null
    ) {
        when (mediaType) {
            SelectedMediaType.MOVIE -> movieRepository.deleteMovie(movie!!)
            SelectedMediaType.TV -> tvRepository.deleteTv(tv!!)
            else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
        }
    }
}