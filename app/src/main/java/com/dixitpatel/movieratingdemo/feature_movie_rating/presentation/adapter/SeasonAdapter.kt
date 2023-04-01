package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.ItemSeasonBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.Season

class SeasonAdapter(private val tvId: Int) : ListAdapter<Season, SeasonAdapter.ViewHolder>(
    DIFF_CALLBACK
) {
    inner class ViewHolder(val view: ItemSeasonBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_season, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.apply {
            tvId = this@SeasonAdapter.tvId
            season = getItem(position)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Season>() {
            override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
                return oldItem.seasonNumber == newItem.seasonNumber
            }

            override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
                return oldItem == newItem
            }
        }
    }
}