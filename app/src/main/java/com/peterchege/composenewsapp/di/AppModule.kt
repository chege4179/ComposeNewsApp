package com.peterchege.scrollmall.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.provider.SyncStateContract
import androidx.room.Room
import com.google.firebase.messaging.FirebaseMessagingService
import com.peterchege.scrollmall.api.ScrollmallApi
import com.peterchege.scrollmall.room.database.ScrollMallDatabase
import com.peterchege.scrollmall.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideScrollMallApi():ScrollmallApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SyncStateContract.Constants.BASE_URL)
            .build()
            .create(ScrollmallApi::class.java)
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
