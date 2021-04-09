package com.example.notificationmusic

import android.content.ContentUris
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.music_noti.view.*

class ListAdapter(val datas: List<Music>) : RecyclerView.Adapter<ListAdapter.MyViewHolder>(){

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
        holder.layout.imgMusic.load(ContentUris.withAppendedId( Uri.parse("content://media/external/audio/album"), datas[position].albumId))
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}