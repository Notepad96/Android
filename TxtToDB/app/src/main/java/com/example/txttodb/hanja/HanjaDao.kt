package com.example.jnote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HanjaDao {

    @Insert
    fun insert(hanja: Hanja)

    /* 삽입 */
    @Query("INSERT INTO hanja_list(level, word, phonation, mean) values(:level, :word, :phonation, :mean)")
    fun insertHanja(level: Int,
    word: String,
    phonation: String,
    mean: String)

    /* 삭제 */
    @Query("DELETE FROM hanja_list WHERE id = :id")
    fun deleteHanja(id: Long)

    @Query("DELETE FROM hanja_list")
    fun deleteAll()

    /* 탐색 */
    @Query("SELECT * FROM hanja_list")
    fun getListAll(): List<Hanja>

    @Query("SELECT * FROM hanja_list WHERE level = :level")
    fun getListLevel(level: Int): List<Hanja>

}