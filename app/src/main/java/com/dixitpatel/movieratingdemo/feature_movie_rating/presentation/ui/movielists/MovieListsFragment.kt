package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.movielists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.FragmentMovieListsBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.LifecycleRecyclerView
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.playYouTubeVideo
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.MovieAdapter
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListsFragment : BaseFragment<FragmentMovieListsBinding>(R.layout.fragment_movie_lists) {

    val viewModel: MovieListsViewModel by activityViewModels()

    override val bindingInvoked: (FragmentMovieListsBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterTrending = MovieAdapter(isTrending = true) { playTrailer(it) }
    val adapterPopular = MovieAdapter()
    val adapterTopRated = MovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.apply {
            addObserver(LifecycleRecyclerView(binding.rvPopular))
            addObserver(LifecycleRecyclerView(binding.rvTopRated))
        }
        collectFlows(listOf(::collectTrendingMovies, ::collectPopularMovies, ::collectTopRatedMovies, ::collectUiState))
    }

    fun playTrailer(movieId: Int) {
        val videoKey = viewModel.getTrendingMovieTrailer(movieId)
        if (videoKey.isEmpty()) showSnackBar(
            message = getString(R.string.trending_trailer_error),
            anchor = true
        ) else activity?.playYouTubeVideo(videoKey)
    }

    private suspend fun collectTrendingMovies() {
        viewModel.trendingMovies.collect { trendingMovies ->
            adapterTrending.submitList(trendingMovies.take(10))
            mediator = TabLayoutMediator(binding.tabLayout, binding.vpTrendings) { _, _ -> }
            mediator?.attach()
        }
    }

    private suspend fun collectPopularMovies() {
        viewModel.popularMovies.collect { popularMovies ->
            adapterPopular.submitList(popularMovies)
        }
    }

    private suspend fun collectTopRatedMovies() {
        viewModel.topRatedMovies.collect { topRatedMovies ->
            adapterTopRated.submitList(topRatedMovies)
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