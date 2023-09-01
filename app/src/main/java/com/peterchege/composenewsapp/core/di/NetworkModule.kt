package com.peterchege.composenewsapp.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.peterchege.composenewsapp.BuildConfig
import com.peterchege.composenewsapp.core.api.NewsApi
import com.peterchege.composenewsapp.core.util.Constants
import com.peterchege.composenewsapp.data.NetworkInfoRepositoryImpl
import com.peterchege.composenewsapp.domain.repository.NetworkInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }



    @Provides
    @Singleton
    fun provideOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE
        return HttpLoggingInterceptor().also {
            it.level = level
        }
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context = context)
            .collector(ChuckerCollector(context = context))
            .maxContentLength(length = 250000L)
            .redactHeaders(headerNames = emptySet())
            .alwaysReadResponseBody(enable = false)
            .build()
    }
    @Provides
    @Singleton
    fun provideNewsApi(httpClient: OkHttpClient,networkJson: Json): NewsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkInfoRepository(
        @ApplicationContext context: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): NetworkInfoRepository {
        return NetworkInfoRepositoryImpl(context = context, ioDispatcher = ioDispatcher)
    }




}