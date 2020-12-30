package com.example.jnote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(Hanja::class, Mark::class), version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun hanjaDao(): HanjaDao

    companion object {
        private var Instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (Instance == null) {
                synchronized(AppDataBase::class) {
                    Instance = Room.databaseBuilder(
                            context,
                            AppDataBase::class.java,
                            "hanja"
                    ).build()
                }
            }
            return Instance
        }

        fun deleteInstance() {
            Instance = null
        }
    }
}