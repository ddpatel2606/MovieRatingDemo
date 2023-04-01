package com.dixitpatel.movieratingdemo.di


import com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository.MovieRepositoryImpl
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.repository.TvRepositoryImpl
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.MovieRepository
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.repository.TvRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun bindTvRepository(repository: TvRepositoryImpl): TvRepository
}