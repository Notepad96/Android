package com.example.alertdialog

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_dialog.view.*

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
        val myLayout = layoutInflater.inflate(R.layout.custom_dialog, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        dialog.show()

        myLayout.okBtn.setOnClickListener {
            Toast.makeText(view.context, "OK Button Click", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        myLayout.cancelBtn.setOnClickListener {
            Toast.makeText(view.context, "Cancel Button Click", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

    }
}