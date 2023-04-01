package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.movielists

import androidx.lifecycle.viewModelScope
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.LIST_ID_POPULAR
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.LIST_ID_TOP_RATED
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.Movie
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.MovieList
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
open class MovieListsViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val getTrendingVideosUseCase: GetTrendingVideosUseCase
) : BaseViewModel() {

    private val _trendingMovies = MutableStateFlow(emptyList<Movie>())
    val trendingMovies get() = _trendingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow(emptyList<Movie>())
    val popularMovies get() = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow(emptyList<Movie>())
    val topRatedMovies get() = _topRatedMovies.asStateFlow()

    private var pagePopular = 1
    private var pageTopRated = 1

    private suspend fun fetchList(listId: String? = null) {
        val page = when (listId) {
            LIST_ID_POPULAR -> pagePopular
            LIST_ID_TOP_RATED -> pageTopRated
            else -> null
        }

        getListUseCase(
            mediaType = SelectedMediaType.MOVIE,
            listId = listId,
            page = page,
            region = null
        ).collect { response ->
            when (response) {
                is Resource.Success -> {
                    val movieList = (response.data as MovieList).results
                    when (listId) {
                        LIST_ID_POPULAR -> _popularMovies.value += movieList
                        LIST_ID_TOP_RATED -> _topRatedMovies.value += movieList
                        else -> _trendingMovies.value = movieList
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
            coroutineScope { fetchList(type) }
            initUiState()
        }
    }

    fun getTrendingMovieTrailer(movieId: Int) = runBlocking {
        var videoKey = ""

        coroutineScope {
            getTrendingVideosUseCase(SelectedMediaType.MOVIE, movieId).collect { response ->
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
                fetchList()
                fetchList(LIST_ID_POPULAR)
                fetchList(LIST_ID_TOP_RATED)
            }
            initUiState()
        }
    }

    init {
        initRequests()
    }
}