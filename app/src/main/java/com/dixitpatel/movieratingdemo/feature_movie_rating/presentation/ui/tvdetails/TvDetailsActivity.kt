package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.tvdetails

import android.os.Bundle
import androidx.activity.viewModels
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.ActivityTvDetailsBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.isDarkMode
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.playYouTubeVideo
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvDetailsActivity : BaseActivity<ActivityTvDetailsBinding>(R.layout.activity_tv_details) {

    private val viewModel: TvDetailsViewModel by viewModels()

    val adapterVideos = VideoAdapter { playYouTubeVideo(it) }
    val adapterCast = PersonAdapter(isCast = true)
    val adapterSeasons by lazy { SeasonAdapter(id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(if (isDarkMode(this)) R.style.DetailDarkTheme else R.style.DetailLightTheme)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.initRequest(id)
        collectFlows(listOf(::collectDetails, ::collectUiState))
    }

    private suspend fun collectDetails() {
        viewModel.details.collect { details ->
            adapterCast.submitList(details.credits.cast)
            adapterVideos.submitList(details.videos.filterVideos())
            adapterSeasons.submitList(details.seasons)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackBar(state.errorText!!, getString(R.string.button_retry)) {
                viewModel.retryConnection {
                    viewModel.initRequest(id)
                }
            }
        }
    }
}