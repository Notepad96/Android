package com.example.jnote.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Hanja::class, Bookmark::class), version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun hanjaDao(): HanjaDao
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        private var Instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (Instance == null) {
                synchronized(AppDataBase::class) {
                    Instance = Room.databaseBuilder(
                            context,
                            AppDataBase::class.java,
                            "hanja"
                    )
                            .build()
                }
            }
            return Instance
        }

        fun deleteInstance() {
            Instance = null
        }
    }
}