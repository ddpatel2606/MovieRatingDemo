package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.favoritemovies.FavoriteMoviesFragment
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.favorites.FavoritesFragment
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.favoritetvs.FavoriteTvsFragment
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.home.HomeFragment
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.movielists.MovieListsFragment
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.tvlists.TvListsFragment
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.ILLEGAL_ARGUMENT_FRAGMENT_TYPE

class FragmentAdapter(private val fragment: Fragment) :
    FragmentStateAdapter(fragment.childFragmentManager, fragment.viewLifecycleOwner.lifecycle) {

    private val homeFragments = listOf(MovieListsFragment(), TvListsFragment())
    private val favoritesFragments = listOf(FavoriteMoviesFragment(), FavoriteTvsFragment())

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (fragment) {
            is HomeFragment -> homeFragments[position]
            is FavoritesFragment -> favoritesFragments[position]
            else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_FRAGMENT_TYPE)
        }
    }
}