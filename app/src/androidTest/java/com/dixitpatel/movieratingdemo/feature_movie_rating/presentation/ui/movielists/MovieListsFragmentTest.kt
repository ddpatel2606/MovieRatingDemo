package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.movielists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MovieListsFragmentTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    //JUnit Test Rule that swaps the background executor used by the Architecture Components
    @get:Rule(order = 1)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun testFragmentViewDisplayedInUiAndClickEvent() {
        Espresso.onView(withId(R.id.vpTrendings))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Intents.init()
        Espresso.onView(withId(R.id.tvSeeAllTopRated)).perform(ViewActions.click())
        Intents.intended(hasComponent(hasClassName("com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.seeall.SeeAllActivity")))
        Intents.release()
    }
}