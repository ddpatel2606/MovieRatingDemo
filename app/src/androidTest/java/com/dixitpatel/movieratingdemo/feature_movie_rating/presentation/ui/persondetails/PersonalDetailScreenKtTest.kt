package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.persondetails

import androidx.compose.material.Surface
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dixitpatel.movieratingdemo.HiltTestActivity
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetDetailsUseCase
import com.dixitpatel.movieratingdemo.ui.theme.MovieRatingDemoTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class PersonalDetailScreenKtTest {

    // inject all the rules for DI
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    // inject all the rules for compose framework
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    @Inject
    lateinit var movieRepository: MovieRepository

    @Inject
    lateinit var tvRepository: TvRepository

    private lateinit var viewModel: PersonDetailsViewModel

    @Mock
    private lateinit var getDetailsUseCase: GetDetailsUseCase

    // inject all the dependencies will use data from Testing DI Module.
    private fun injectAndSetupDi() {
        hiltRule.inject()

        getDetailsUseCase = GetDetailsUseCase(movieRepository,tvRepository)
        viewModel = PersonDetailsViewModel(getDetailsUseCase)
        setContent()
    }

    private fun setContent() {
        composeTestRule.setContent {
            MovieRatingDemoTheme {
                Surface {
                    viewModel.initRequest(22)
                    PersonalDetailScreen(
                        viewModel = viewModel,
                        onBackButtonClicked = {}
                    )
                }
            }
        }
    }

    @Test
    fun testPersonalDataDisplayed() {
        injectAndSetupDi()
        composeTestRule.waitForIdle()
        findImageView("image_personal").assertIsDisplayed()

        composeTestRule
            .onNodeWithText("The Rock")
            .performScrollTo()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.place_of_birth))
            .performScrollTo()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.place_of_birth))
            .assertIsDisplayed()

        //findTextField("The Rock").assertIsDisplayed()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.birthday))
            .performScrollTo()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.birthday))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.department))
            .performScrollTo()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.department))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.gender))
            .performScrollTo()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.gender))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.biography))
            .performScrollTo()

        composeTestRule
            .onNodeWithText(activity.getString(R.string.biography))
            .assertIsDisplayed()

    }

    private fun findTextField(text: String): SemanticsNodeInteraction {
        return composeTestRule.onNode(
            hasSetTextAction() and hasText(text)
        )
    }


    private fun findImageView(text: String): SemanticsNodeInteraction {
        return composeTestRule.onNodeWithContentDescription(text)
    }

}