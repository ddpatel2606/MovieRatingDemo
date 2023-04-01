package com.dixitpatel.movieratingdemo.di

import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.api_interface.MovieApiInterface
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.api_interface.TvApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleTest {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://127.0.0.1:8080")
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    fun provideMovieApiInterface(retrofit: Retrofit): MovieApiInterface =
        retrofit.create(MovieApiInterface::class.java)

    @Provides
    fun provideTvApiInterface(retrofit: Retrofit): TvApiInterface =
        retrofit.create(TvApiInterface::class.java)
}