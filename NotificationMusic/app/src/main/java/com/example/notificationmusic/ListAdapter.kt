package com.example.notificationmusic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.music_noti.view.*

class ListAdapter(val datas: List<MusicInfo>) : RecyclerView.Adapter<ListAdapter.MyViewHolder>(){

    class MyViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)

        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.layout.textMainTitle.text = datas[position].title
        holder.layout.textMusicInfo.text = datas[position].info
        holder.layout.textDuration.text = android.text.format.DateFormat.format("mm:ss", datas[position].duration)

    }

    override fun getItemCount(): Int {
        return datas.size
    }
}