package com.example.machinetest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter( val recyclerlist: List<RecylerviewData>) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val text = itemView.findViewById<TextView>(R.id.text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recyclerlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = recyclerlist[position]
        holder.text.text = currentItem.name
        holder.image.setImageResource(currentItem.image)
    }
}