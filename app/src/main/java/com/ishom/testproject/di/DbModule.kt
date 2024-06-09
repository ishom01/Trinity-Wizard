package com.ishom.testproject.di

import android.content.Context
import com.ishom.testproject.data.db.AppDatabase
import com.ishom.testproject.presentation.ui.detail.UserSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideSession(@ApplicationContext context: Context): UserSession {
        return UserSession(context)
    }
}