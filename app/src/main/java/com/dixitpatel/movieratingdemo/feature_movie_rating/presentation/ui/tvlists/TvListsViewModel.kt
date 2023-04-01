package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.tvlists

import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.LIST_ID_POPULAR
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.LIST_ID_TOP_RATED
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.Tv
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.TvList
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetListUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetTrendingVideosUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.Resource
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.SelectedMediaType
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.UiState
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TvListsViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val getTrendingVideosUseCase: GetTrendingVideosUseCase
) : BaseViewModel() {

    private val _trendingTvShows = MutableStateFlow(emptyList<Tv>())
    val trendingTvShows get() = _trendingTvShows.asStateFlow()

    private val _popularTvShows = MutableStateFlow(emptyList<Tv>())
    val popularTvShows get() = _popularTvShows.asStateFlow()

    private val _topRatedTvShows = MutableStateFlow(emptyList<Tv>())
    val topRatedTvShows get() = _topRatedTvShows.asStateFlow()

    private var pagePopular = 1
    private var pageTopRated = 1

    private suspend fun fetchLists(listId: String? = null) {
        val page = when (listId) {
            LIST_ID_POPULAR -> pagePopular
            LIST_ID_TOP_RATED -> pageTopRated
            else -> null
        }

        getListUseCase(mediaType = SelectedMediaType.TV, listId = listId, page = page).collect { response ->
            when (response) {
                is Resource.Success -> {
                    val tvList = (response.data as TvList).results

                    when (listId) {
                        LIST_ID_POPULAR -> _popularTvShows.value += tvList
                        LIST_ID_TOP_RATED -> _topRatedTvShows.value += tvList
                        else -> _trendingTvShows.value = tvList
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

        when (type as String) {
            LIST_ID_POPULAR -> pagePopular++
            LIST_ID_TOP_RATED -> pageTopRated++
        }

        viewModelScope.launch {
            coroutineScope { fetchLists(type) }
            initUiState()
        }
    }

    fun getTrendingTvTrailer(tvId: Int) = runBlocking {
        var videoKey = ""

        coroutineScope {
            getTrendingVideosUseCase(SelectedMediaType.TV, tvId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        videoKey = response.data.filterVideos(true).last().key
                        _uiState.value = UiState.successState()
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.errorState(false, response.message)
                    }
                }
            }
        }

        videoKey
    }

    fun initRequests() {
        viewModelScope.launch {
            coroutineScope {
                fetchLists()
                fetchLists(LIST_ID_POPULAR)
                fetchLists(LIST_ID_TOP_RATED)
            }
            initUiState()
        }
    }

    init {
        initRequests()
    }
}