package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.favorites

import android.os.Bundle
import android.view.View
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseFragment
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.FragmentFavoritesBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    override val bindingInvoked: ((FragmentFavoritesBinding) -> Unit)?
        get() = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = FragmentAdapter(this)

        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabTitles = listOf(getString(R.string.tab_title_1), getString(R.string.tab_title_2))
            tab.text = tabTitles[position]
        }

        mediator?.attach()
    }
}