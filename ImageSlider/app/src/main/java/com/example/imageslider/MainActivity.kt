package com.example.imageslider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.slide_image_item.*

class MainActivity : AppCompatActivity() {
    var sliderHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sliderItems: MutableList<SliderItems> = mutableListOf()
        sliderItems.add(SliderItems(R.drawable.image1))
        sliderItems.add(SliderItems(R.drawable.image2))
        sliderItems.add(SliderItems(R.drawable.image3))
        sliderItems.add(SliderItems(R.drawable.image4))
        sliderItems.add(SliderItems(R.drawable.image5))

        imageSlider.adapter = SliderAdapter(sliderItems, imageSlider)

        imageSlider.clipToPadding = false
        imageSlider.clipChildren = false
        imageSlider.offscreenPageLimit = 3
        imageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        var compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40));
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer { page, position ->
          var r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        })

        imageSlider.setPageTransformer(compositePageTransformer)

        imageSlider.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 2000)
            }
        })
    }

    val sliderRunnable = Runnable {
        imageSlider.setCurrentItem(imageSlider.currentItem + 1)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }
}