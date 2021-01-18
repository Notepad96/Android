package com.example.viewcapture

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun capture(view: View) {
        val capture = getBitmapFromView(mainLayout)

        imageView.setImageBitmap(capture)

        var folderPath = Environment.getExternalStorageDirectory().absolutePath + "capture"
        val folder = File(folderPath)
        if(folder.exists()) folder.mkdirs()

        var filePath = folderPath +  "/" + System.currentTimeMillis() + ".png"
        val file = File(filePath)
        Toast.makeText(this, "$filePath", Toast.LENGTH_SHORT).show()
        //capture?.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))
    }

    open fun getBitmapFromView(view: View): Bitmap? {
        var bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    open fun getBitmapFromView(view: View, defaultColor: Int): Bitmap? {
        var bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        canvas.drawColor(defaultColor)
        view.draw(canvas)
        return bitmap
    }

}