package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.tvlists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.FragmentTvListsBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.LifecycleRecyclerView
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.playYouTubeVideo
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.TvAdapter
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvListsFragment : BaseFragment<FragmentTvListsBinding>(R.layout.fragment_tv_lists) {

    private val viewModel: TvListsViewModel by activityViewModels()

    override val bindingInvoked: (FragmentTvListsBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterTrending = TvAdapter(isTrending = true) { playTrailer(it) }
    val adapterPopular = TvAdapter()
    val adapterTopRated = TvAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.apply {
            addObserver(LifecycleRecyclerView(binding.rvPopular))
            addObserver(LifecycleRecyclerView(binding.rvTopRated))
        }

        collectFlows(listOf(::collectTrendingTvs, ::collectPopularTvs, ::collectTopRatedTvs, ::collectUiState))
    }

    private fun playTrailer(tvId: Int) {
        val videoKey = viewModel.getTrendingTvTrailer(tvId)
        if (videoKey.isEmpty()) showSnackBar(
            message = getString(R.string.trending_trailer_error),
            anchor = true
        ) else activity?.playYouTubeVideo(videoKey)
    }

    private suspend fun collectTrendingTvs() {
        viewModel.trendingTvShows.collect { trendingTvs ->
            adapterTrending.submitList(trendingTvs.take(10))
            mediator = TabLayoutMediator(binding.tabLayout, binding.vpTrendings) { _, _ -> }
            mediator?.attach()
        }
    }

    private suspend fun collectPopularTvs() {
        viewModel.popularTvShows.collect { popularTvs ->
            adapterPopular.submitList(popularTvs)
        }
    }

    private suspend fun collectTopRatedTvs() {
        viewModel.topRatedTvShows.collect { topRatedTvs ->
            adapterTopRated.submitList(topRatedTvs)
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackBar(
                message = state.errorText!!,
                actionText = getString(R.string.button_retry),
                anchor = true
            ) {
                viewModel.retryConnection {
                    viewModel.initRequests()
                }
            }
        }
    }
}