package com.dixitpatel.movieratingdemo.di

import android.content.Context
import com.dixitpatel.movieratingdemo.BuildConfig
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.api_interface.MovieApiInterface
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.remote.api_interface.TvApiInterface
import com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils.NetworkUtils
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()

    @Singleton
    @Provides
    @Named(INTERCEPTOR_FOR_REQUEST)
    fun provideRequestInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .addQueryParameter("language", "en")
            .addQueryParameter(
                "include_image_language",
                "en"
            )
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()

        chain.proceed(request)
    }

    @Singleton
    @Provides
    @Named(INTERCEPTOR_FOR_CACHE)
    fun provideCacheInterceptor(@ApplicationContext context: Context): Interceptor = Interceptor { chain ->
        val headerValue = if (NetworkUtils.isConnected(context))
        /*
        *  If there is Internet, get the cache that was stored 30 min ago.
        *  If the cache is older than 30 min, then discard it,
        *  and indicate an error in fetching the response.
        *  The 'max-age' attribute is responsible for this behavior.
        */
            "public, max-age=$CACHE_MAX_AGE"
        else
        /*
        *  If there is no Internet, get the cache that was stored 7 days ago.
        *  If the cache is older than 7 days, then discard it,
        *  and indicate an error in fetching the response.
        *  The 'max-stale' attribute is responsible for this behavior.
        *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
        */
            "public, only-if-cached, max-stale=$CACHE_MAX_STALE_TIME"

        val request = chain.request()
            .newBuilder()
            .header("Cache-Control", headerValue)
            .build()
        chain.proceed(request)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        @Named(INTERCEPTOR_FOR_CACHE) cacheInterceptor: Interceptor,
        @Named(INTERCEPTOR_FOR_REQUEST) requestInterceptor: Interceptor,
        stethoInterceptor: StethoInterceptor
    ): OkHttpClient {
        val trustAllCerts = NetworkUtils.allSSLTrustedManager()
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        return OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, CACHE_SIZE))
            .addInterceptor(cacheInterceptor)
            .addInterceptor(requestInterceptor)
            .addNetworkInterceptor(stethoInterceptor)
            .connectTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideMovieApiInterface(retrofit: Retrofit): MovieApiInterface =
        retrofit.create(MovieApiInterface::class.java)

    @Singleton
    @Provides
    fun provideTvApiInterface(retrofit: Retrofit): TvApiInterface =
        retrofit.create(TvApiInterface::class.java)

}