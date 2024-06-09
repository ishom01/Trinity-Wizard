package com.ishom.testproject.di

import com.ishom.testproject.data.local.UserLocalDataStore
import com.ishom.testproject.data.local.UserLocalDataStoreImpl
import com.ishom.testproject.data.remote.UserRemoteDataStore
import com.ishom.testproject.data.remote.UserRemoteDataStoreImpl
import com.ishom.testproject.data.repository.UserRepositoryImpl
import com.ishom.testproject.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserDataStoreModule {

    @Binds
    fun provideUserRemoteDataStore(remote: UserRemoteDataStoreImpl): UserRemoteDataStore

    @Binds
    fun provideUserLocalDataStore(remote: UserLocalDataStoreImpl): UserLocalDataStore

    @Binds
    fun provideUserRepository(repo: UserRepositoryImpl): UserRepository
}