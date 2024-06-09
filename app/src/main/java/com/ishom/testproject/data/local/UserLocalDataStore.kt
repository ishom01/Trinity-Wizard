package com.ishom.testproject.data.local

import com.ishom.testproject.domain.entity.UserEntity

interface UserLocalDataStore {
    suspend fun get(name: String) : List<UserEntity>
    suspend fun save(users: List<UserEntity>)

    suspend fun add(user: UserEntity)

    suspend fun login(name: String, email: String) : Boolean

    suspend fun byIds(id: String) : UserEntity?

    suspend fun getProfile() : UserEntity?

    suspend fun edit(user: UserEntity)

    suspend fun saveSession(id: String)
    suspend fun logout()

    suspend fun isLogin(): Boolean
}