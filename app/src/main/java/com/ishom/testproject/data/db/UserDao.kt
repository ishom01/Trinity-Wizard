package com.ishom.testproject.data.db

import androidx.paging.PagingSource
import androidx.room.*
import com.ishom.testproject.domain.entity.UserEntity

@Dao
interface UserDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(users: List<UserEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Transaction
    @Query("UPDATE user SET firstName = :firstName, lastName = :lastName, email = :email, phone =:phoneNumber WHERE id = :id")
    suspend fun update(id: String, firstName: String, lastName: String, email: String?, phoneNumber: String?)

    @Query("SELECT * FROM user WHERE firstName LIKE (:key) OR lastName LIKE (:key)")
    fun all(key: String): List<UserEntity>

    @Query("SELECT * FROM user where id = :id ORDER BY id ASC LIMIT 1")
    fun detail(id: String): UserEntity?

    @Query("SELECT * FROM user where email = (:email) AND firstName = (:firstName) AND lastName = (:lastName) ORDER BY id ASC LIMIT 1")
    fun find(firstName: String, lastName: String, email: String): UserEntity?

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun delete(id: Long)

    @Transaction
    @Query("DELETE FROM user")
    suspend fun clear()
}