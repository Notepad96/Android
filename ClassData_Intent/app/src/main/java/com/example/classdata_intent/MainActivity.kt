package com.example.classdata_intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun click(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        intent.apply {
            putExtra("action", 1)
            putExtra("data", Book(1, "abc cho", 12000))
        }
        startActivity(intent)
    }

    fun click2(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        val bookList = mutableListOf<Book>()
        bookList.add(Book(1, "Book1", 23000))
        bookList.add(Book(2, "Book2", 53000))
        bookList.add(Book(3, "Book3", 35000))

        val temp: ArrayList<Parcelable> = arrayListOf()
        temp.addAll(bookList)

        intent.apply {
            putExtra("action", 2)
            putParcelableArrayListExtra("dataList", temp)
        }
        startActivity(intent)
    }
}