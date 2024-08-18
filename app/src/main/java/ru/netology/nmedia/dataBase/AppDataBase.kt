package ru.netology.nmedia.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PostEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract val postDao: PostDaoInterface

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context): AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "app_data_base")
            .allowMainThreadQueries()
            .build()
    }
}