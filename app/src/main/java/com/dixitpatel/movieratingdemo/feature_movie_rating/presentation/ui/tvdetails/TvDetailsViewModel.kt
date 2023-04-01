package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.tvdetails

import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.FavoriteTv
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.TvDetail
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
class TvDetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    private val checkFavoritesUseCase: CheckFavoritesUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase
) : BaseViewModel() {

    private val _details = MutableStateFlow(TvDetail.empty)
    val details get() = _details.asStateFlow()

    private val _isInFavorites = MutableStateFlow(false)
    val isInFavorites get() = _isInFavorites.asStateFlow()

    private lateinit var favoriteTv: FavoriteTv

    private fun fetchTvDetails() {
        viewModelScope.launch {
            getDetailsUseCase(SelectedMediaType.TV, id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        (response.data as TvDetail).apply {
                            _details.value = this
                            favoriteTv = FavoriteTv(
                                id = id,
                                episodeRunTime = if (episodeRunTime.isEmpty()) 0 else episodeRunTime[0],
                                firstAirDate = firstAirDate,
                                name = name,
                                posterPath = posterPath,
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
            _isInFavorites.value = checkFavoritesUseCase(SelectedMediaType.TV, id)
        }
    }

    fun updateFavorites() {
        viewModelScope.launch {
            if (_isInFavorites.value) {
                deleteFavoriteUseCase(mediaType = SelectedMediaType.TV, tv = favoriteTv)
                _isInFavorites.value = false
            } else {
                addFavoriteUseCase(mediaType = SelectedMediaType.TV, tv = favoriteTv)
                _isInFavorites.value = true
            }
        }
    }

    fun initRequest(tvId: Int) {
        id = tvId
        checkFavorites()
        fetchTvDetails()
    }
}