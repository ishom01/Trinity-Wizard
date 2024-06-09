package com.ishom.testproject.data.repository

import com.ishom.testproject.data.local.UserLocalDataStore
import com.ishom.testproject.data.remote.UserRemoteDataStore
import com.ishom.testproject.domain.entity.UserEntity
import com.ishom.testproject.domain.repository.UserRepository
import com.ishom.testproject.presentation.ui.detail.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataStore,
    private val local: UserLocalDataStore,
): UserRepository {
    override suspend fun search(query: String, isForced: Boolean) = withContext(Dispatchers.IO) {
        if (isForced) {
            val jsonUsers = remote.get()
            local.save(jsonUsers)
        }
        return@withContext local.get(name = query)
    }

    override suspend fun add(firstName: String, lastName: String, email: String?, phone: String?) = withContext(Dispatchers.IO) {
        local.add(
            UserEntity(
                id = UUID.randomUUID().toString(),
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone
            )
        )
    }

    override suspend fun edit(
        id: String,
        firstName: String,
        lastName: String,
        email: String?,
        phone: String?
    ) = withContext(Dispatchers.IO) {
        local.edit(
            UserEntity(
                id = id,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone
            )
        )
    }
    override suspend fun login(name: String, email: String): Boolean = withContext(Dispatchers.IO) {
        val jsonUsers = remote.get()
        if (local.get("").isEmpty()) {
            local.save(jsonUsers)
        }
        val user = jsonUsers.find {
            it.name.contains(name) && (it.email?.contains(email) == true)
        } ?: return@withContext false
        local.saveSession(user.id)
        return@withContext true
    }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        local.logout()
    }

    override suspend fun getProfile(): UserEntity? = withContext(Dispatchers.IO) {
        return@withContext local.getProfile()
    }

    override suspend fun byId(id: String): UserEntity? = withContext(Dispatchers.IO) {
        return@withContext local.byIds(id)
    }

    override suspend fun isLogin(): Boolean = local.isLogin()

}