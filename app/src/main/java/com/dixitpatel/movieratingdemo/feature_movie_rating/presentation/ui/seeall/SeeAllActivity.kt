package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.seeall

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.base.BaseActivity
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.databinding.ActivitySeeAllBinding
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.model.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.adapter.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeAllActivity : BaseActivity<ActivitySeeAllBinding>(R.layout.activity_see_all) {

    private val viewModel: SeeAllViewModel by viewModels()

    private var mediaType: Parcelable? = null
    private var listId: String? = null
    private var list: List<Any>? = null

    var intentType: Parcelable? = null
    var title: String? = null

    val movieAdapter by lazy { MovieAdapter() }
    val tvAdapter by lazy { TvAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(if (isDarkMode(this)) R.style.SeeAllDarkTheme else R.style.SeeAllLightTheme)
        window.statusBarColor = ContextCompat.getColor(this, R.color.day_night_inverse)
        getIntentExtras()
        binding.activity = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.layoutManager = GridLayoutManager(this, if (intentType == SelectedIntentType.VIDEOS) 2 else 3)
        getList()
    }

    private fun getIntentExtras() {
        intent.apply {
            intentType = parcelable(INTENT_TYPE)
            mediaType = parcelable(MEDIA_TYPE)
            listId = getStringExtra(LIST_ID)
            list = when (intentType) {
                SelectedIntentType.VIDEOS, SelectedIntentType.CAST -> parcelableArrayList<Parcelable>(LIST) as List<Any>
                else -> null
            }
            title = getStringExtra(TITLE)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getList() {
        binding.recyclerView.adapter = when (intentType) {
            SelectedIntentType.VIDEOS -> VideoAdapter(true) { playYouTubeVideo(it) }.apply { submitList(list as List<Video>) }
            SelectedIntentType.CAST -> PersonAdapter(isGrid = true, isCast = true).apply { submitList(list as List<Person>) }
            else -> {
                when (mediaType) {
                    SelectedMediaType.MOVIE -> movieAdapter
                    SelectedMediaType.TV -> tvAdapter
                    else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
                }.also { collectFlows(listOf(::collectListResult, ::collectUiState)) }
            }
        }

        viewModel.initRequest(intentType, mediaType, id, listId)
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun collectListResult() {
        viewModel.results.collect { results ->
            when (mediaType) {
                SelectedMediaType.MOVIE -> {
                    val movieList = results as Set<Movie>
                    movieAdapter.submitList(movieList.toList())
                }
                SelectedMediaType.TV -> {
                    val tvList = results as Set<Tv>
                    tvAdapter.submitList(tvList.toList())
                }
                else -> throw IllegalArgumentException(ILLEGAL_ARGUMENT_MEDIA_TYPE)
            }
        }
    }

    private suspend fun collectUiState() {
        viewModel.uiState.collect { state ->
            if (state.isError) showSnackBar(state.errorText!!, getString(R.string.button_retry)) {
                viewModel.retryConnection {
                    viewModel.initRequest(intentType, mediaType, id, listId)
                }
            }
        }
    }
}