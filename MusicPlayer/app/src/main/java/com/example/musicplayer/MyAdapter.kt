package com.example.musicplayer

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat

class MyAdapter(val datas: MutableList<Music>, val mp: SharedPreferences) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val artUri: Uri = Uri.parse("content://media/external/audio/albumart")

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
        holder.layout.txtListArtist.text = datas[position].artist
        holder.layout.txtListTitle.text = datas[position].title
        holder.layout.txtListTime.text = SimpleDateFormat("mm:ss").format(datas[position].duration)

        holder.layout.layoutList.setOnClickListener {
            mp.edit {
                putInt("pos", position)
                val intent = Intent( holder.layout.context, MusicDetail::class.java)
                ContextCompat.startActivity(holder.layout.context, intent, null)
            }
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}