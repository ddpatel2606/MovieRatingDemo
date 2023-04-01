package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.favoritetvs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.FavoriteTv
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
class FavoriteTvsViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) : ViewModel() {

    private val _favoriteTvs = MutableStateFlow(emptyList<FavoriteTv>())
    val favoriteTvs get() = _favoriteTvs.asStateFlow()

    fun fetchFavoriteTvs() {
        viewModelScope.launch {
            getFavoritesUseCase(SelectedMediaType.TV).collect {
                @Suppress("UNCHECKED_CAST")
                _favoriteTvs.value = it as List<FavoriteTv>
            }
        }
    }

    fun removeTvFromFavorites(tv: FavoriteTv) {
        viewModelScope.launch {
            deleteFavoriteUseCase(mediaType = SelectedMediaType.TV, tv = tv)
            fetchFavoriteTvs()
        }
    }

    fun addTvToFavorites(tv: FavoriteTv) {
        viewModelScope.launch {
            addFavoriteUseCase(mediaType = SelectedMediaType.TV, tv = tv)
            fetchFavoriteTvs()
        }
    }

    init {
        fetchFavoriteTvs()
    }
}