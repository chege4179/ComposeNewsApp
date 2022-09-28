package com.peterchege.composenewsapp.di

import com.peterchege.composenewsapp.api.NewsApi
import com.peterchege.composenewsapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(NewsApi::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideScrollMallDatabase(app: Application): ScrollMallDatabase {
//        return Room.databaseBuilder(
//            app,
//            ScrollMallDatabase::class.java,
//            SyncStateContract.Constants.DATABASE_NAME
//        ).build()
//    }
}
