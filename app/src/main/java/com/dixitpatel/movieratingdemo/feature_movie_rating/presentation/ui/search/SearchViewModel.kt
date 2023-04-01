package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.search

import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.ILLEGAL_ARGUMENT_MEDIA_TYPE
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetSearchResultsUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.UiState
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchResultsUseCase: GetSearchResultsUseCase) : BaseViewModel() {

    private val _isSearchInitialized = MutableStateFlow(false)
    val isSearchInitialized get() = _isSearchInitialized.asStateFlow()

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()

    private val _movieResults = MutableStateFlow(emptyList<Movie>())
    val movieResults get() = _movieResults.asStateFlow()

    private val _movieTotalResults = MutableStateFlow(0)
    val movieTotalResults get() = _movieTotalResults.asStateFlow()

    private val _tvResults = MutableStateFlow(emptyList<Tv>())
    val tvResults get() = _tvResults.asStateFlow()

    private val _tvTotalResults = MutableStateFlow(0)
    val tvTotalResults get() = _tvTotalResults.asStateFlow()

    private var pageMovie = 1
    private var pageTv = 1

    private var isQueryChanged = false

    private suspend fun fetchSearchResults(mediaType: SelectedMediaType) {
        val page = when (mediaType) {
            SelectedMediaType.MOVIE -> pageMovie
            SelectedMediaType.TV -> pageTv
            else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
        }

        getSearchResultsUseCase(mediaType, query.value, page).collect { response ->
            when (response) {
                is Resource.Success -> {
                    when (mediaType) {
                        SelectedMediaType.MOVIE -> {
                            val movieList = response.data as MovieList
                            _movieResults.value = if (isQueryChanged) movieList.results else _movieResults.value + movieList.results
                            _movieTotalResults.value = movieList.totalResults
                        }
                        SelectedMediaType.TV -> {
                            val tvList = response.data as TvList
                            _tvResults.value = if (isQueryChanged) tvList.results else _tvResults.value + tvList.results
                            _tvTotalResults.value = tvList.totalResults
                        }
                        else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
                    }
                    areResponsesSuccessful.add(true)
                    isInitial = false
                }
                is Resource.Error -> {
                    errorText = response.message
                    areResponsesSuccessful.add(false)
                }
            }
        }
    }

    fun onLoadMore(type: Any?) {
        _uiState.value = UiState.loadingState(isInitial)
        isQueryChanged = false

        when (type as SelectedMediaType) {
            SelectedMediaType.MOVIE -> pageMovie++
            SelectedMediaType.TV -> pageTv++
            else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
        }

        viewModelScope.launch {
            coroutineScope { fetchSearchResults(type) }
            initUiState()
        }
    }

    fun fetchInitialSearch(query: String) {
        _uiState.value = UiState.loadingState(isInitial)
        _isSearchInitialized.value = true
        _query.value = query

        isQueryChanged = true
        isInitial = true

        pageMovie = 1
        pageTv = 1

        initRequests()
    }

    fun clearSearchResults() {
        _isSearchInitialized.value = false
        _query.value = ""

        _movieResults.value = emptyList()
        _tvResults.value = emptyList()
    }

    fun initRequests() {
        viewModelScope.launch {
            coroutineScope {
                    fetchSearchResults(SelectedMediaType.MOVIE)
                    fetchSearchResults(SelectedMediaType.TV)
            }
            initUiState()
        }
    }
}