package com.ishom.testproject.domain.repository

import androidx.paging.PagingData
import com.ishom.testproject.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun search(query: String, isForced: Boolean = false): List<UserEntity>
    suspend fun add(firstName: String, lastName: String, email: String?, phone: String?)
    suspend fun edit(id: String, firstName: String, lastName: String, email: String?, phone: String?)

    suspend fun login(name: String, email: String): Boolean

    suspend fun logout()

    suspend fun getProfile(): UserEntity?

    suspend fun byId(id: String): UserEntity?

    suspend fun isLogin(): Boolean
}