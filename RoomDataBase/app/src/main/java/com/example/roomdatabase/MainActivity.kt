package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.database.Book
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = AppDataBase.getInstance(this)

        CoroutineScope(Dispatchers.IO).launch {

            //db?.bookDao()?.insertBook(Book("가나나", "mine", 32000))
            //db?.bookDao()?.insertBooks(Book("김가가", "mine2", 22000), Book("이가마", "mine3", 19000))
            //db?.bookDao()?.myInsertBook("마나다", "mymymy", 21000)

            //db?.bookDao()?.deleteBook(4)
        }

    }

    fun insert(view: View) {
        var name = if(insertName.text.isBlank()) "Empty" else insertName.text.toString()
        var writer = if(insertWriter.text.isBlank()) "Empty" else insertWriter.text.toString()
        var price = if(insertPrice.text.isBlank()) 0 else insertPrice.text.toString().toInt()

        CoroutineScope(Dispatchers.IO).launch {
            db?.bookDao()?.insertBook(
                Book(name, writer, price)
            )
        }
    }

    fun delete(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            db?.bookDao()?.deleteBook( deleteID.text.toString().toInt() )
        }
    }

    fun listUpdate(view: View) {
        var count = 0
        var list: List<Book> = listOf()
        CoroutineScope(Dispatchers.IO).launch {
            count = db?.bookDao()?.getCount()!!
            list = db?.bookDao()?.getAll()!!
            list?.forEach { Log.d("Testing", it.toString()) }
        }

        if(list.size == 0 ) {
            textView.text = "데이터가 없습니다."
        } else {
            list?.forEach { Log.d("Testing", it.toString()) }
            textView.text = list?.joinToString("\n")
        }
    }
}