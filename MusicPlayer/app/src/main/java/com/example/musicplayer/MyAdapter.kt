package com.example.musicplayer

import android.content.ContentUris
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(val datas: MutableList<Music>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val artUri = Uri.parse("content://media/external/audio/albumart")

    class MyViewHolder(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.layout.imgList.load(ContentUris.withAppendedId(artUri, datas[position].album_id)) {
            crossfade(true)
            placeholder(R.drawable.empty)
            error(R.drawable.empty)
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}