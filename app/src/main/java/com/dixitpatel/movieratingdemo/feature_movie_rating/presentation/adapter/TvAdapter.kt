package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dixitpatel.movieratingdemo.databinding.ItemTrendingTvBinding
import com.dixitpatel.movieratingdemo.databinding.ItemTvBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.Tv

class TvAdapter(
    private val isTrending: Boolean = false,
    private val onTrendingFabClick: ((Int) -> Unit)? = null
) : ListAdapter<Tv, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    inner class HorizontalViewHolder private constructor(val view: ItemTvBinding) : RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class TrendingViewHolder private constructor(val view: ItemTrendingTvBinding) : RecyclerView.ViewHolder(view.root) {
        constructor(parent: ViewGroup) : this(ItemTrendingTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
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
                    tv = getItem(position)
                }
            }
            is TrendingViewHolder -> holder.view.tv = getItem(position)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tv>() {
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }
    }
}