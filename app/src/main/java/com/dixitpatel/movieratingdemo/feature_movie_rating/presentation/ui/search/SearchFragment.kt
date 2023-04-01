package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.FragmentSearchBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.LifecycleRecyclerView
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.MovieAdapter
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.TvAdapter
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by activityViewModels()

    override val bindingInvoked: (FragmentSearchBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterMovies by lazy { MovieAdapter() }
    val adapterTvs by lazy { TvAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.apply {
            addObserver(LifecycleRecyclerView(binding.rvMovies))
            addObserver(LifecycleRecyclerView(binding.rvTvs))
        }

        setupSearchView()
        collectFlows(listOf(::collectMovieSearchResults, ::collectTvSearchResults, ::collectUiState))
    }

    fun clearSearch() {
        viewModel.clearSearchResults()
        adapterMovies.submitList(null)
        adapterTvs.submitList(null)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.rvMovies.scrollToPosition(0)
                binding.rvTvs.scrollToPosition(0)

                if (!query.isNullOrEmpty()) viewModel.fetchInitialSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private suspend fun collectMovieSearchResults() {
        viewModel.movieResults.collect { movies ->
            adapterMovies.submitList(movies)
        }
    }

    private suspend fun collectTvSearchResults() {
        viewModel.tvResults.collect { tvs ->
            adapterTvs.submitList(tvs)
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