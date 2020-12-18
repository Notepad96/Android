package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.example.database.Book
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    var db: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.getInstance(this)
    }

    fun insert(view: View) {
        val name = if(insertName.text.isBlank()) "Empty" else insertName.text.toString()
        val writer = if(insertWriter.text.isBlank()) "Empty" else insertWriter.text.toString()
        val price = if(insertPrice.text.isBlank()) 0 else insertPrice.text.toString().toInt()

        CoroutineScope(Dispatchers.IO).launch {
            db?.bookDao()?.insertBook(
                Book(name, writer, price)
            )
        }
        Toast.makeText(this, "삽입 성공!", Toast.LENGTH_SHORT).show()
        insertName.setText("")
        insertWriter.setText("")
        insertPrice.setText("")
    }

    fun delete(view: View) {
        val id = if(deleteID.text.isBlank()) 0L else deleteID.text.toString().toLong()

        CoroutineScope(Dispatchers.IO).launch {
            val check = db?.bookDao()?.isBook( id )

            CoroutineScope(Dispatchers.Main).launch {
                if( check == 1)
                    Toast.makeText(applicationContext, "삭제 성공!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(applicationContext, "해당 ID 없음!", Toast.LENGTH_SHORT).show()
            }
            db?.bookDao()?.deleteBook( id )
        }
        deleteID.setText("")
    }

    fun update(view: View) {
        val id = updateID.text.toString().toLong()
        val value = updateValue.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            when(view.id) {
                R.id.unBtn -> db?.bookDao()?.updateName(id, value)
                R.id.uwBtn -> db?.bookDao()?.updateWriter(id, value)
                R.id.upBtn -> {
                    if(value.isDigitsOnly())
                        db?.bookDao()?.updatePrice(id, value.toInt())
                    else
                        db?.bookDao()?.updatePrice(id, 0)
                }
            }
        }
        updateID.setText("")
        updateValue.setText("")
    }

    fun listUpdate(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            var count = db?.bookDao()?.getCount()
            var list = db?.bookDao()?.getAll()

            CoroutineScope(Dispatchers.Main).launch {
                if(count == 0 ) {
                    textView.text = "데이터가 없습니다."
                } else {
                    list?.forEach { Log.d("Testing", it.toString()) }
                    textView.text = list?.joinToString("\n")
                }
            }
        }
    }

}