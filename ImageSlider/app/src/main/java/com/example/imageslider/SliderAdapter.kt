package com.example.imageslider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.slide_image_item.view.*

class SliderAdapter(var sliderItems: MutableList<SliderItems>, var viewPager2: ViewPager2) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setImage(sliderItems: SliderItems) {
            itemView.slideImage.load(sliderItems.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slide_image_item, parent, false))
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems.get(position))
        if(position == sliderItems.size -2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    private var runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

}