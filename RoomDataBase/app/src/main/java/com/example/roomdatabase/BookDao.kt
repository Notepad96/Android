package com.example.database

import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(vararg books: Book)

    @Insert
    fun insertBook(book: Book)

    @Query("INSERT INTO books(book_name, writer, price) VALUES (:name, :writer, :price)")
    fun myInsertBook(name: String, writer: String, price: Int)

    @Query("DELETE FROM books where id = :id")
    fun deleteBook(id: Int)     // id로 삭제

    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT book_name FROM books")
    fun getNameAll(): List<String>

    @Query("SELECT * FROM books where id = :id")
    fun getBook(id: Long): Book

    @Query("SELECT COUNT(*) FROM books")
    fun getCount(): Int

}