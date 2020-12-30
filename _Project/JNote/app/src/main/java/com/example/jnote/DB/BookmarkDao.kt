package com.example.jnote.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarkDao {
    /* 삽입 */
    @Insert
    fun insert(hanja: Hanja)

    @Query("INSERT INTO bookmark(level, word, phonation, mean) values(:level, :word, :phonation, :mean)")
    fun insertHanja(level: Int,
                    word: String,
                    phonation: String,
                    mean: String)

    /* 삭제 */
    @Query("DELETE FROM bookmark WHERE id = :id")
    fun deleteHanja(id: Long)

    @Query("DELETE FROM bookmark")
    fun deleteAll()

    /* 탐색 */
    @Query("SELECT * FROM bookmark")
    fun getListAll(): List<Hanja>

    @Query("SELECT * FROM bookmark WHERE level = :level")
    fun getListLevel(level: Int): List<Hanja>

}