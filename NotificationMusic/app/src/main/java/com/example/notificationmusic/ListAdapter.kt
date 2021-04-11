package com.example.notificationmusic

import android.content.ContentUris
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.music_noti.view.*

class ListAdapter(val datas: List<Music>) : RecyclerView.Adapter<ListAdapter.MyViewHolder>(){
    val artUri = Uri.parse("content://media/external/audio/albumart")

    class MyViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as LinearLayout

        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d("musicList", datas[position].album_id.toString())

        holder.layout.listTitle.text = datas[position].title
        holder.layout.listArtist.text = datas[position].artist
        holder.layout.listDuration.text = android.text.format.DateFormat.format("mm:ss", datas[position].duration)


        holder.layout.listImg.load(ContentUris.withAppendedId(artUri, datas[position].album_id))
        if(holder.layout.listImg.drawable == null) holder.layout.listImg.load(R.drawable.empty)

    }

    override fun getItemCount(): Int {
        return datas.size
    }
}