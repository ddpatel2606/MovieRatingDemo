package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.favoritetvs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseFragment
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.FragmentFavoriteTvsBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.FavoriteTv
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.LifecycleRecyclerView
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.FavoriteTvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTvsFragment : BaseFragment<FragmentFavoriteTvsBinding>(R.layout.fragment_favorite_tvs) {

    private val viewModel: FavoriteTvsViewModel by activityViewModels()

    override val bindingInvoked: (FragmentFavoriteTvsBinding) -> Unit
        get() = { binding ->
            binding.fragment = this
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
        }

    val adapterFavorites = FavoriteTvAdapter { removeTv(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleRecyclerView(binding.recyclerView))
        collectFlows(listOf(::collectFavoriteTvs))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchFavoriteTvs()
    }

    private fun removeTv(tv: FavoriteTv) {
        viewModel.removeTvFromFavorites(tv)

        showSnackBar(
            message = getString(R.string.snackbar_removed_item),
            actionText = getString(R.string.snackbar_action_undo),
            anchor = true
        ) {
            viewModel.addTvToFavorites(tv)
        }
    }

    private suspend fun collectFavoriteTvs() {
        viewModel.favoriteTvs.collect { favoriteTvs ->
            adapterFavorites.submitList(favoriteTvs)
        }
    }
}