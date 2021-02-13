package com.example.recyclerview2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(private val myData: Array<String>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    class MyViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.layout.itemImage.setImageResource(R.drawable.ic_launcher_foreground)

        holder.layout.itemText.text = myData[position]
        holder.layout.itemBtn.text = "Button $position"
    }

    override fun getItemCount() = myData.size
}