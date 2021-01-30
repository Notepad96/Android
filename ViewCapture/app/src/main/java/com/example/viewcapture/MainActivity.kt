package com.example.viewcapture

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun capture(view: View) {
        var capture = getBitmapFromView(mainLayout, Color.GREEN)

        var storage = cacheDir
        var fileName = SimpleDateFormat("yyMMdd_HHmmss").format(Date()) + ".jpg"
        var imgFile = File(storage, fileName)
        try {
            imgFile.createNewFile()
            var out = FileOutputStream(imgFile)
            capture!!.compress(Bitmap.CompressFormat.JPEG, 10, out)
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Toast.makeText(this, "${storage}/${fileName}", Toast.LENGTH_SHORT).show()
        Glide.with(this)
                .load(imgFile)
                .into(imageView)
    }


    private fun getBitmapFromView(view: View, defaultColor: Int = Color.WHITE): Bitmap? {
        var bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        canvas.drawColor(defaultColor)
        view.draw(canvas)
        return bitmap
    }

}