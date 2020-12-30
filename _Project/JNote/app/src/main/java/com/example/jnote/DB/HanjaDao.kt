package com.example.jnote.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HanjaDao {
    /* 삽입 */
    @Insert
    fun insert(hanja: Hanja)

    @Query("INSERT INTO hanja(level, word, phonation, mean) values(:level, :word, :phonation, :mean)")
    fun insertHanja(level: Int,
                    word: String,
                    phonation: String,
                    mean: String)

    /* 삭제 */
    @Query("DELETE FROM hanja WHERE id = :id")
    fun deleteHanja(id: Long)

    @Query("DELETE FROM hanja")
    fun deleteAll()

    /* 탐색 */
    @Query("SELECT * FROM hanja")
    fun getListAll(): List<Hanja>

    @Query("SELECT * FROM hanja WHERE level = :level")
    fun getListLevel(level: Int): List<Hanja>

    @Query("SELECT COUNT(*) FROM hanja")
    fun getCount(): Long
}