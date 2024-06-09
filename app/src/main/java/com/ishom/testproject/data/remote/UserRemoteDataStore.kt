package com.ishom.testproject.data.remote

import com.ishom.testproject.domain.entity.UserEntity


interface UserRemoteDataStore {

    suspend fun get() : List<UserEntity>
}