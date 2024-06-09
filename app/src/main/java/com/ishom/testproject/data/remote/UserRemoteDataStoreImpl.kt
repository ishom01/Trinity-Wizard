package com.ishom.testproject.data.remote

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ishom.testproject.domain.entity.UserEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class UserRemoteDataStoreImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : UserRemoteDataStore {

    override suspend fun get(): List<UserEntity> {
        val typeToken = object : TypeToken<List<UserEntity>>() {}.type
        return Gson().fromJson<List<UserEntity>?>(jsonFile(), typeToken)
    }

    fun jsonFile(): String {
        val file = appContext.assets.open("data.json")
        val bufferedReader = BufferedReader(InputStreamReader(file))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        return stringBuilder.toString()
    }
}