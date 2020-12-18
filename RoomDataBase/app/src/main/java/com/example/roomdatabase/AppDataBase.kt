package com.example.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.database.Book
import com.example.database.BookDao

@Database(entities = arrayOf(Book::class) ,version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun bookDao() : BookDao

    companion object {
        private var Instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if(Instance == null) {
                synchronized(AppDataBase::class) {
                    Instance = Room.databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        "hanja"
                    ).build()
                    // migration // .addMigrations(MIGRATION_1_2)
                }
            }
            return Instance
        }

        fun deleteInstance() {
            Instance = null
        }

        // 마이그레이션 부분 (생략)
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `books2` (`id` LONG, `name` TEXT, `writer` TEXT, `price` INTEGER, " +
                        "PRIMARY KEY(`id`))")
            }
        }
    }

}