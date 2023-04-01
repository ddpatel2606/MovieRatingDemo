package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.favoritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.FavoriteMovie
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.AddFavoriteUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.DeleteFavoriteUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetFavoritesUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow(emptyList<FavoriteMovie>())
    val favoriteMovies get() = _favoriteMovies.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    fun fetchFavoriteMovies() {
        viewModelScope.launch {
            getFavoritesUseCase(SelectedMediaType.MOVIE).collect {
                _favoriteMovies.value = it as List<FavoriteMovie>
            }
        }
    }

    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            deleteFavoriteUseCase(mediaType = SelectedMediaType.MOVIE, movie = movie)
            fetchFavoriteMovies()
        }
    }

    fun addMovieToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            addFavoriteUseCase(mediaType = SelectedMediaType.MOVIE, movie = movie)
            fetchFavoriteMovies()
        }
    }

    init {
        fetchFavoriteMovies()
    }
}