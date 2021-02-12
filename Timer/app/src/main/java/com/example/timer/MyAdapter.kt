package com.example.timer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val myData: ArrayList<TimeRecord>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{

    class MyViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false) )
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.layout.findViewById<TextView>(R.id.TR1).text = myData[position].lap.toString()
        holder.layout.findViewById<TextView>(R.id.TR2).text =
            timeToString(myData[position].lapTime)
        holder.layout.findViewById<TextView>(R.id.TR3).text =
            timeToString(myData[position].allTime)

    }

    override fun getItemCount() = myData.size

    private fun timeToString(time: Int): String {
        val min = if(time / 6000 < 10) "0${time/6000}" else "${time/6000}"
        val sec = if( (time / 100) % 60 < 10) "0${(time / 100) % 60}" else "${(time / 100) % 60}"
        val milli = if(time % 100 < 10) "0${time % 100}" else "${time % 100}"

        return "${min}:${sec}.${milli}"
    }
}