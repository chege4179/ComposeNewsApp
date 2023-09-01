package com.peterchege.composenewsapp.core.di

import android.content.Context
import androidx.room.Room
import com.peterchege.composenewsapp.core.room.database.ComposeNewsAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideComposeNewsAppDatabase(@ApplicationContext context: Context): ComposeNewsAppDatabase {
        return Room
            .databaseBuilder(context, ComposeNewsAppDatabase::class.java, "news.db",)
            .fallbackToDestructiveMigration()
            .build()
    }
}