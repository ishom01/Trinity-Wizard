package com.ishom.testproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ishom.testproject.domain.entity.UserEntity

@Database(
    version = 1,
    entities = [UserEntity::class],
    exportSchema = false,
)
abstract class AppDatabase: RoomDatabase() {

    abstract val userDao: UserDao
    
    companion object {
        
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "contact_database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}