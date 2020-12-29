package com.example.jnote

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HanjaDao {

    /* 삽입 */
    @Insert
    fun insertHanja(hanja: Hanja)

    /* 삭제 */
    @Query("DELETE FROM hanja_list WHERE id = :id")
    fun deleteHanja(id: Long)

    @Query("SELECT * FROM hanja_list")
    fun deleteAll()

    /* 탐색 */
    @Query("SELECT * FROM hanja_list")
    fun getListAll(): List<Hanja>

    @Query("SELECT * FROM hanja_list WHERE level = :level")
    fun getListLevel(level: Int): List<Hanja>


}