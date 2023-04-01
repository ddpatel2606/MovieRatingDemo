package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.moviedetails

import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.FavoriteMovie
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.MovieDetail
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.AddFavoriteUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.CheckFavoritesUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.DeleteFavoriteUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetDetailsUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.UiState
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val checkFavoritesUseCase: CheckFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) : BaseViewModel() {

    private val _details = MutableStateFlow(MovieDetail.empty)
    val details get() = _details.asStateFlow()

    private val _isInFavorites = MutableStateFlow(false)
    val isInFavorites get() = _isInFavorites.asStateFlow()

    private lateinit var favoriteMovie: FavoriteMovie

    private fun fetchMovieDetails() {
        viewModelScope.launch {
            getDetailsUseCase(SelectedMediaType.MOVIE, id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        (response.data as MovieDetail).apply {
                            _details.value = this
                            favoriteMovie = FavoriteMovie(
                                id = id,
                                posterPath = posterPath,
                                releaseDate = releaseDate,
                                runtime = runtime,
                                title = title,
                                voteAverage = voteAverage,
                                voteCount = voteCount,
                                date = System.currentTimeMillis()
                            )
                        }
                        _uiState.value = UiState.successState()
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(errorText = response.message)
                    }
                }
            }
        }
    }

    private fun checkFavorites() {
        viewModelScope.launch {
            _isInFavorites.value = checkFavoritesUseCase(SelectedMediaType.MOVIE, id)
        }
    }

    fun updateFavorites() {
        viewModelScope.launch {
            if (_isInFavorites.value) {
                deleteFavoriteUseCase(mediaType = SelectedMediaType.MOVIE, movie = favoriteMovie)
                _isInFavorites.value = false
            } else {
                addFavoriteUseCase(mediaType = SelectedMediaType.MOVIE, movie = favoriteMovie)
                _isInFavorites.value = true
            }
        }
    }

    fun initRequests(movieId: Int) {
        id = movieId
        checkFavorites()
        fetchMovieDetails()
    }
}