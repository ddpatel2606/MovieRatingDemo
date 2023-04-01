package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class LifecycleRecyclerView(private val recyclerView: RecyclerView) : DefaultLifecycleObserver {
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        recyclerView.adapter = null
    }
}