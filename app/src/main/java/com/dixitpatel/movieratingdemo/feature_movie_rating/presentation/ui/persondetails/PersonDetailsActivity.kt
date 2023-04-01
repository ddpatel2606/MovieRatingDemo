package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.persondetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.DETAIL_ID
import com.dixitpatel.movieratingdemo.ui.theme.MovieRatingDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailsActivity : ComponentActivity() {

    val id by lazy { intent.getIntExtra(DETAIL_ID, 0) }

    private val viewModel: PersonDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieRatingDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    viewModel.initRequest(id)
                    PersonalDetailScreen(viewModel, onBackButtonClicked =
                    { onBackPressedCallback.handleOnBackPressed() }
                    )
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

}