package com.ishom.testproject.data.local

import androidx.room.withTransaction
import com.ishom.testproject.data.db.AppDatabase
import com.ishom.testproject.domain.entity.UserEntity
import com.ishom.testproject.presentation.ui.detail.UserSession
import java.util.UUID
import javax.inject.Inject

class UserLocalDataStoreImpl @Inject constructor(
    private val db: AppDatabase,
    private val userSession: UserSession,
): UserLocalDataStore {
    override suspend fun get(name: String): List<UserEntity> {
        val users = db.userDao.all("%$name%")
        users.find { it.id == userSession.userId }?.isYou = true
        return users
    }

    override suspend fun save(users: List<UserEntity>) {
        db.withTransaction {
            db.userDao.let {
                it.clear()
                it.inserts(users)
            }
        }
    }

    override suspend fun add(user: UserEntity) {
        db.withTransaction {
            db.userDao.insert(user)
        }
    }

    override suspend fun login(name: String, email: String): Boolean {
        val names = name.split(" ")
        if (names.isEmpty() && email.isEmpty()) return false
        val firstName = names.first()
        var lastName = ""
        repeat(name.length - 1) { index ->
            lastName += " ${names[index + 1]}"
        }
        if (db.userDao.find("*$firstName*", "*$lastName*", "*$email*") != null) return true
        db.withTransaction {
            db.userDao.insert(UserEntity(id = UUID.randomUUID().toString(), firstName = firstName, lastName = lastName, email = email))
        }
        return true
    }

    override suspend fun byIds(id: String): UserEntity? {
        return db.userDao.detail(id)
    }

    override suspend fun getProfile(): UserEntity? {
        return  db.userDao.detail(userSession.userId ?: "")
    }

    override suspend fun edit(user: UserEntity) {
        db.withTransaction {
            db.userDao.update(user.id, user.firstName, user.lastName, user.email, user.phone)
        }
    }

    override suspend fun saveSession(id: String) {
        userSession.userId = id
    }

    override suspend fun logout() {
        userSession.userId = null
    }

    override suspend fun isLogin(): Boolean {
        return !userSession.userId.isNullOrEmpty()
    }
}