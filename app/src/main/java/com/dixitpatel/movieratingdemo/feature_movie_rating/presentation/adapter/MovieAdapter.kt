package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dixitpatel.movieratingdemo.databinding.ItemMovieBinding
import com.dixitpatel.movieratingdemo.databinding.ItemTrendingMovieBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.Movie

class MovieAdapter(
    private val isTrending: Boolean = false,
    private val onTrendingFabClick: ((Int) -> Unit)? = null
) : ListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    inner class HorizontalViewHolder private constructor(val view: ItemMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class TrendingViewHolder private constructor(val view: ItemTrendingMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(
            ItemTrendingMovieBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        ) {
            view.fabTrailer.setOnClickListener {
                onTrendingFabClick?.invoke(getItem(layoutPosition).id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isTrending) TrendingViewHolder(parent) else HorizontalViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> {
                holder.view.apply {
                    movie = getItem(position)
                }
            }
            is TrendingViewHolder -> holder.view.movie = getItem(position)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}