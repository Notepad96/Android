package com.example.alertdialog

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dialog(view: View) {
        AlertDialog.Builder(view.context).apply {
            setTitle("알림창")
            setMessage("메세지입니다. 메세지입니다. 메세지입니다. 메세지입니다.")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(view.context, "OK Button Click", Toast.LENGTH_SHORT).show()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(view.context, "Cancel Button Click", Toast.LENGTH_SHORT).show()
            })
            show()
        }
    }

    fun cuDialog(view: View) {

    }
}