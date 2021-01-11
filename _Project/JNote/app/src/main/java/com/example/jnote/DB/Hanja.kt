package com.example.jnote.DB

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "hanja")
data class Hanja(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                 var level: Int,
                 var word: String?,
                 var phonation: String?,
                 var mean: String?,
) : Parcelable {

    override fun toString(): String {
        return "$id, $level, $word, $phonation, $mean"
    }

}