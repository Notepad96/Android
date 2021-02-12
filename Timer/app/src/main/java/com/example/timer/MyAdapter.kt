package com.example.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val myData: Array<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    class MyViewHolder(val layout: ConstraintLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) as ConstraintLayout

        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.layout.findViewById<ImageView>(R.id.imageView).setImageResource(R.drawable.ic_launcher_foreground)
        holder.layout.findViewById<TextView>(R.id.textView).text = myData[position]
        holder.layout.findViewById<TextView>(R.id.textView2).text = "${myData[position]}123"
    }

    override fun getItemCount() = myData.size
}