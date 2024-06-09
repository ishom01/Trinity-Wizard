package com.ishom.testproject.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey()
    var id: String,
    val firstName: String,
    val lastName: String,
    var email: String? = null,
    var dob: String? = null,
    var phone: String? = null,
    var isYou: Boolean = false,
)  {
    val code: String get() {
        return "${firstName.firstOrNull()}${lastName.firstOrNull() ?: ""}";
    }

    val name: String get() {
        return "$firstName $lastName";
    }
}