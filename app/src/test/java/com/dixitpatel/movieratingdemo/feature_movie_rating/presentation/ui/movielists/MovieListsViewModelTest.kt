package com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.movielists

import com.dixitpatel.movieratingdemo.di.DBModule
import com.dixitpatel.movieratingdemo.di.RepositoryModule
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository.FakeMovieRepositoryImplTest
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetListUseCase
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.usecases.GetTrendingVideosUseCase
import com.dixitpatel.movieratingdemo.utils.MainCoroutineRule
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@UninstallModules(value = [RepositoryModule::class, DBModule::class])
class MovieListsViewModelTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    private lateinit var viewModel: MovieListsViewModel

    private lateinit var getListUseCase: GetListUseCase
    private lateinit var getTrendingVideosUseCase: GetTrendingVideosUseCase

    @Inject
    lateinit var movieRepository: MovieRepository

    @Inject
    lateinit var tvRepository: TvRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        getListUseCase = GetListUseCase(movieRepository, tvRepository)
        getTrendingVideosUseCase = GetTrendingVideosUseCase(movieRepository, tvRepository)
        viewModel = MovieListsViewModel(getListUseCase, getTrendingVideosUseCase)
    }

    @Test
    fun testMovieList() = runTest {
        viewModel.initRequests()
        Truth.assertThat(viewModel.trendingMovies.value).isNotEmpty()
        Truth.assertThat(viewModel.popularMovies.value).isNotEmpty()
        Truth.assertThat(viewModel.topRatedMovies.value).isNotEmpty()
    }

    @Test
    fun testFailedMovieList() = runTest {
        movieRepository = FakeMovieRepositoryImplTest()
        getListUseCase = GetListUseCase(movieRepository, tvRepository)
        getTrendingVideosUseCase = GetTrendingVideosUseCase(movieRepository, tvRepository)
        viewModel = MovieListsViewModel(getListUseCase, getTrendingVideosUseCase)

        viewModel.initRequests()
        Truth.assertThat(viewModel.uiState.value.isError).isTrue()
        Truth.assertThat(viewModel.trendingMovies.value).isEmpty()
        Truth.assertThat(viewModel.popularMovies.value).isEmpty()
        Truth.assertThat(viewModel.topRatedMovies.value).isEmpty()
    }

    @Test
    fun testTrendingMovieTrailer() = runTest{
        viewModel.getTrendingMovieTrailer(76600)
        advanceUntilIdle()
        Truth.assertThat(viewModel.uiState.value.isSuccess).isTrue()


        movieRepository = FakeMovieRepositoryImplTest()
        getListUseCase = GetListUseCase(movieRepository, tvRepository)
        getTrendingVideosUseCase = GetTrendingVideosUseCase(movieRepository, tvRepository)
        viewModel = MovieListsViewModel(getListUseCase, getTrendingVideosUseCase)

        viewModel.getTrendingMovieTrailer(76600)
        advanceUntilIdle()
        Truth.assertThat(viewModel.uiState.value.isError).isTrue()
    }
}