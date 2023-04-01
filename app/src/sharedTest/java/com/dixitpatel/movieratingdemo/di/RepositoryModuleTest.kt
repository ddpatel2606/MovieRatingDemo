package com.dixitpatel.movieratingdemo.di

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository.TestMovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository.TestTvRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object RepositoryModuleTest {

    @Provides
    @Singleton
    fun provideMovieRepository(): MovieRepository {
        return TestMovieRepository()
    }

    @Provides
    @Singleton
    fun provideTvRepository(): TvRepository {
        return TestTvRepository()
    }
}