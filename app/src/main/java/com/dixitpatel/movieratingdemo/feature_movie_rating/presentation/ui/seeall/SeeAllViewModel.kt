package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.seeall

import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.ILLEGAL_ARGUMENT_MEDIA_TYPE
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.MovieList
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.TvList
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetListUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetSearchResultsUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedIntentType
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.UiState
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val getSearchResultsUseCase: GetSearchResultsUseCase
) : BaseViewModel() {

    private val _results = MutableStateFlow(setOf<Any>())
    val results get() = _results.asStateFlow()

    private var intentType: Parcelable? = null
    private var mediaType: Parcelable? = null
    private var detailId: Int? = null
    private var listId: String? = null

    private var page = 1

    private fun fetchList() {
        viewModelScope.launch {
            getListUseCase(
                mediaType = mediaType as SelectedMediaType,
                listId = listId,
                page = page,
                region = ""
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _results.value += when (mediaType) {
                            SelectedMediaType.MOVIE -> (response.data as MovieList).results
                            SelectedMediaType.TV -> (response.data as TvList).results
                            else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
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

    private fun fetchSearchResults() {
        viewModelScope.launch {
            getSearchResultsUseCase(
                mediaType = mediaType as SelectedMediaType,
                query = listId!!,
                page = page
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _results.value += when (mediaType) {
                            SelectedMediaType.MOVIE -> (response.data as MovieList).results
                            SelectedMediaType.TV -> (response.data as TvList).results
                            else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
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

    fun onLoadMore(@Suppress("UNUSED_PARAMETER") type: Any?) {
        _uiState.value = UiState.loadingState()
        page++

        when (intentType) {
            SelectedIntentType.LIST -> fetchList()
            SelectedIntentType.SEARCH -> fetchSearchResults()
        }
    }

    fun initRequest(intentType: Parcelable?, mediaType: Parcelable?, detailId: Int?, listId: String?) {
        this.intentType = intentType
        this.mediaType = mediaType
        this.detailId = detailId
        this.listId = listId

        when (intentType) {
            SelectedIntentType.LIST -> fetchList()
            SelectedIntentType.SEARCH -> fetchSearchResults()
            else -> _uiState.value = UiState.successState()
        }
    }
}