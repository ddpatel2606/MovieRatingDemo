package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.favoritemovies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseFragment
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.FragmentFavoriteMoviesBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.FavoriteMovie
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.LifecycleRecyclerView
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.FavoriteMovieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : BaseFragment<FragmentFavoriteMoviesBinding>(R.layout.fragment_favorite_movies) {

    private val viewModel: FavoriteMoviesViewModel by activityViewModels()

    override val bindingInvoked: (FragmentFavoriteMoviesBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterFavorites = FavoriteMovieAdapter { removeMovie(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleRecyclerView(binding.recyclerView))
        collectFlows(listOf(::collectFavoriteMovies))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteMovies()
    }

    private fun removeMovie(movie: FavoriteMovie) {
        viewModel.removeMovieFromFavorites(movie)
        showSnackBar(
            message = getString(R.string.snackbar_removed_item),
            actionText = getString(R.string.snackbar_action_undo),
            anchor = true
        ) {
            viewModel.addMovieToFavorites(movie)
        }
    }

    private suspend fun collectFavoriteMovies() {
        viewModel.favoriteMovies.collect { favoriteMovies ->
            adapterFavorites.submitList(favoriteMovies)
        }
    }
}