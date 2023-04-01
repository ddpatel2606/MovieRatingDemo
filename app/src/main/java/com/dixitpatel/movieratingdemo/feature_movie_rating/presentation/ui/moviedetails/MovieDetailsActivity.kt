package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.moviedetails

import android.os.Bundle
import androidx.activity.viewModels
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.ActivityMovieDetailsBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.isDarkMode
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.playYouTubeVideo
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.PersonAdapter
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.VideoAdapter
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding>(R.layout.activity_movie_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()

    val adapterVideos = VideoAdapter { playYouTubeVideo(it) }
    val adapterCast = PersonAdapter(isCast = true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(if (isDarkMode(this)) R.style.DetailDarkTheme else R.style.DetailLightTheme)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.initRequests(id)
        collectFlows(listOf(::collectDetails, ::collectUiState))
    }

    private suspend fun collectDetails() {
        viewModel.details.collect { details ->
            adapterCast.submitList(details.credits.cast)
            adapterVideos.submitList(details.videos.filterVideos())
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackBar(state.errorText!!, getString(R.string.button_retry)) {
                viewModel.retryConnection {
                    viewModel.initRequests(id)
                }
            }
        }
    }
}