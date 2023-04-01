package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dixitpatel.movieratingdemo.R
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule(order = 1)
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun testAllFragmentsWhenNavigationGraphIsDisplayed() {
        onView(withId(R.id.bottom_nav_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.homeFragment)).check(matches(isDisplayed()))

        onView(withId(R.id.searchFragment)).perform(ViewActions.click())
        onView(withId(R.id.searchFragment)).check(matches(isDisplayed()))

        onView(withId(R.id.favoritesFragment)).perform(ViewActions.click())
        onView(withId(R.id.favoritesFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun testMainActivityOnBackPressClick(){
        onView(withId(R.id.bottom_nav_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.favoritesFragment)).perform(ViewActions.click())

        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.homeFragment)).check(matches(isDisplayed()))

        onView(isRoot()).perform(pressBack())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.double_press_exit)))

        Espresso.pressBackUnconditionally()
        Truth.assertThat(activityScenarioRule.scenario.state).isEqualTo(androidx.lifecycle.Lifecycle.State.DESTROYED)
    }

}