package com.example.paintboard

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var pb: PaintBoard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pb = PaintBoard(this)
        paintBoard.addView(pb)

        textSizeBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textSizeView.text = "Text Size : $progress"
                pb.size = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    fun clear(view: View) {
        pb.pathList.clear()
        pb.invalidate()
    }

    fun actionBack(view: View) {
        pb.pathList.removeLastOrNull()

        for(i in pb.pathList.size-1..0) {
            if(pb.pathList[i].act != pb.START) {
                pb.pathList.removeLastOrNull()
            } else break
        }
        pb.invalidate()
    }

    fun erase(view: View) {
        pb.color = Color.WHITE
    }

    fun colorSelect(view: View) {
        pb.color = Color.BLACK
    }
}