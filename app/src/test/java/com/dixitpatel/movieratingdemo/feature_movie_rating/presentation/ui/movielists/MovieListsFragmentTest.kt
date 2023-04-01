package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.movielists

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import com.dixitpatel.movieratingdemo.utils.BaseTest
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.utils.MainCoroutineRule
import com.dixitpatel.movieratingdemo.utils.launchFragmentInHiltContainer
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class MovieListsFragmentTest : BaseTest<MovieListsFragment>() {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var movieRepository: MovieRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    override fun getTestObject(): MovieListsFragment {
        return MovieListsFragment()
    }

    @Before
    override fun setUp() {
        hiltAndroidRule.inject()
        super.setUp()
    }

    @Test
    fun testMoviePlayTrailer() {
        val application =
            shadowOf(ApplicationProvider.getApplicationContext<Context>() as Application)
        launchFragmentInHiltContainer<MovieListsFragment>(
            Bundle(),
            fragmentFactory = fragmentFactory()
        )
        {
            this.playTrailer(10)
            val intent = application.nextStartedActivity
            Truth.assertThat(intent.action).matches(Intent.ACTION_VIEW)
            Truth.assertThat(intent.data)
                .isEquivalentAccordingToCompareTo(Uri.parse("http://www.youtube.com/watch?v=fNx9lIJSQHU"))
        }
    }

}