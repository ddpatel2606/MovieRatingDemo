package com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.dao.MovieDao
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.dao.TvDao
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.entities.FavoriteMovieEntity
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.local.entities.FavoriteTvEntity

@Database(entities = [FavoriteMovieEntity::class, FavoriteTvEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao

    companion object {
        const val DATABASE_NAME = "movie_rating_db"
    }
}