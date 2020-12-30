package com.example.jnote.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark")
data class Bookmark(var level: Int,
                 var word: String?,
                 var phonation: String?,
                 var mean: String?,
)
{
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override fun toString(): String {
        return "$id, $level, $word, $phonation, $mean"
    }
}