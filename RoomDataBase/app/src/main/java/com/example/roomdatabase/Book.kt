package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @ColumnInfo(name = "book_name") var name: String?,
    var writer: String?,
    var price: Int
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override fun toString(): String {
        return "id = $id, name = $name, writer = $writer, price = $price"
    }
}