package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils

interface InfiniteScrollListener {
    fun onLoadMore(type: Any? = null)
}