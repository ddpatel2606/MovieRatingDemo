package com.dixitpatel.movieratingdemo.di

import android.content.Context
import androidx.room.Room
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.dao.MovieDao
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.dao.TvDao
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DBModule::class]
)
object DBModuleTest {

    @Provides
    fun provideTestAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideTestMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Singleton
    @Provides
    fun provideTestTvDao(appDatabase: AppDatabase): TvDao = appDatabase.tvDao()

}
