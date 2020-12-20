package com.example.database

import androidx.room.*

@Dao
interface BookDao {

    /* 삽입 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(vararg books: Book)

    @Insert
    fun insertBook(book: Book)

    @Query("INSERT INTO books(book_name, writer, price) VALUES (:name, :writer, :price)")
    fun myInsertBook(name: String, writer: String, price: Int)

    /* 삭제 */
    @Query("DELETE FROM books where id = :id")
    fun deleteBook(id: Long)

    @Query("DELETE FROM books")
    fun deleteAll()

    /* 업데이트 */
    @Query("UPDATE books SET book_name = :name WHERE id = :id")
    fun updateName(id: Long, name: String)

    @Query("UPDATE books SET writer = :writer WHERE id = :id")
    fun updateWriter(id: Long, writer: String)

    @Query("UPDATE books SET price = :price WHERE id = :id")
    fun updatePrice(id: Long, price: Int)

    /* 탐색 */
    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Query("SELECT book_name FROM books")
    fun getNameAll(): List<String>

    @Query("SELECT * FROM books where id = :id")
    fun getBook(id: Long): Book

    @Query("SELECT COUNT(*) FROM books")
    fun getCount(): Int

    @Query("SELECT * FROM books where book_name LIKE '%:name%'")
    fun searchName(name: String): Book?

    @Query("SELECT EXISTS ( SELECT * FROM books where id = :id)")
    fun isBook(id: Long): Int

}