package com.example.jnote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jnote.DB.AppDataBase
import com.example.jnote.DB.Hanja
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListAdapter(private val context: Context, private val myData: MutableList<Hanja>?, private val mode: Boolean = true, private val mode2: Boolean = true, val book: Boolean = false) :
        RecyclerView.Adapter<ListAdapter.MyHolder>()
{
    private var db: AppDataBase? = null

    init {
        db = AppDataBase.getInstance(context)
    }

    class MyHolder(val layout: LinearLayout) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as LinearLayout

        return MyHolder(layout)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val level = myData?.get(position)?.level ?: 0
        val word = myData?.get(position)?.word ?: ""
        val phonation = myData?.get(position)?.phonation ?: ""
        val mean = myData?.get(position)?.mean ?: ""

        if(book) {
            holder.layout.level.text = level.toString()
            holder.layout.level.visibility = View.VISIBLE

            holder.layout.item_layout.setOnClickListener {
                val popMenu: PopupMenu = PopupMenu(holder.layout.context, holder.layout)
                popMenu.inflate(R.menu.popup_menu2)
                popMenu.setOnMenuItemClickListener {
                    Toast.makeText(context, "$position", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        myData?.removeAt(position)
                        db?.bookmarkDao()?.deleteHanja(myData!!.get(position).id)
                    }
                    notifyDataSetChanged()
                    notifyItemRemoved(position)
                    true
                }
                popMenu.show()
                true
            }
        } else {
            holder.layout.level.visibility = View.GONE

            holder.layout.item_layout.setOnClickListener {
                val popMenu: PopupMenu = PopupMenu(holder.layout.context, holder.layout)
                popMenu.inflate(R.menu.popup_menu)
                popMenu.setOnMenuItemClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        db?.bookmarkDao()?.insertHanja(level, word, phonation, mean)
                    }
                    true
                }
                popMenu.show()
                true
            }
        }


        holder.layout.word.text = word
        holder.layout.phonation.text = phonation
        holder.layout.mean.text = mean

        if(!mode) holder.layout.word.alpha = 0f
        if(!mode2) holder.layout.phonation.alpha = 0f

        holder.layout.word.setOnClickListener {
            holder.layout.word.alpha = if(holder.layout.word.alpha == 1f) 0f
            else 1f
        }
        holder.layout.phonation.setOnClickListener {
            holder.layout.phonation.alpha = if(holder.layout.phonation.alpha == 1f) 0f
            else 1f
        }

    }



    override fun getItemCount() = myData?.size ?: 0
}