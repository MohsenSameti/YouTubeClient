package com.samentic.youtubeclient.core.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.samentic.youtubeclient.core.data.db.YoutubeDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object LocalModule {
    private const val PREF_NAME = "youtube"
    private const val DB_NAME = "youtube"

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideYoutubeDb(context: Context): YoutubeDb {
        return Room.databaseBuilder(context, YoutubeDb::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}