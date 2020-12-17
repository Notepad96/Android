package com.example.database

import androidx.room.*

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(vararg books: Book)

    @Insert
    fun insertBook(book: Book)

    @Update
    fun updateBooks(vararg books: Book)


    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT book_name FROM books")
    fun getNameAll(): List<String>


    @Query("SELECT * FROM books where id = :id")
    fun getBook(id: Long): Book


}