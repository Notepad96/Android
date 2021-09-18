package com.example.itemanimation

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_last_item.view.*

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    val LAST_ITEM = 1
    val ITEM = 0

    var myData = listOf("qweqrtqw", "ZXcasd", "etwadfsd")

    class MyViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return if(viewType == LAST_ITEM) {
            MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_last_item, parent, false))
        } else {
            MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(position != myData.size) {
            holder.layout.textItem.text = myData[position]

            holder.layout.textItem.setOnClickListener {
                val intent = Intent(holder.layout.context, MainActivity2::class.java)
                holder.layout.context.startActivity(intent)
                (holder.layout.context as Activity).overridePendingTransition(R.anim.item_top, R.anim.fade_out)

            }
        }
        else {
            holder.layout.testImage.setOnClickListener {
                Toast.makeText(holder.layout.context, "Click Test", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return myData.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == myData.size) {
            LAST_ITEM
        } else {
            ITEM
        }
    }
}