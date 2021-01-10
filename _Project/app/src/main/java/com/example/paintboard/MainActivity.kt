package com.example.paintboard

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ColorPickerDialogListener {
    lateinit var pb: PaintBoard
    var tempColor: Int = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pb = PaintBoard(this)
        paintBoard.addView(pb)

        textSizeBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textSizeView.text = "Size : $progress"
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

        for(i in 1..4) {
            pb.pathList.removeLastOrNull()
        }
        pb.invalidate()
    }

    fun erase(view: View) {
        if(eraseBtn.text == "지우개") {
            pb.color = Color.WHITE
            eraseBtn.text = "연 필"
        } else {
            pb.color = tempColor
            eraseBtn.text = "지우개"
        }
    }

    fun colorSelect(view: View) {
        ColorPickerDialog.newBuilder()
            .setColor(pb.color)
            .setShowAlphaSlider(true)
            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
            .show(this)
    }
    override fun onColorSelected(dialogId: Int, color: Int) {
        pb.color = color
        tempColor = color
        eraseBtn.text = "지우개"
        colorSelectBtn.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

}