package com.dixitpatel.movieratingdemo.di

import android.content.Context
import androidx.room.Room
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.dao.MovieDao
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.dao.TvDao
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.database.AppDatabase
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.database.AppDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Singleton
    @Provides
    fun provideTvDao(appDatabase: AppDatabase): TvDao = appDatabase.tvDao()

}