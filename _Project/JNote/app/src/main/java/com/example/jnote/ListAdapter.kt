package com.example.jnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.jnote.DB.Hanja
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(private val myData: List<Hanja>?, private val mode: Boolean = true, private val mode2: Boolean = true) :
        RecyclerView.Adapter<ListAdapter.MyHolder>()
{

    class MyHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as LinearLayout

        return MyHolder(layout)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.layout.word.text = myData?.get(position)?.word ?: ""
        holder.layout.phonation.text = myData?.get(position)?.phonation ?: ""
        holder.layout.mean.text = myData?.get(position)?.mean ?: ""

        if(!mode) holder.layout.word.visibility = View.INVISIBLE
        if(!mode2) holder.layout.phonation.visibility = View.INVISIBLE
    }

    override fun getItemCount() = myData?.size ?: 0
}