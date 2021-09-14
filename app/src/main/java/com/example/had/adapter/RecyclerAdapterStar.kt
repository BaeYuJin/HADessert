package com.example.had.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.had.R
import com.example.had.dataclass.StarData

class RecyclerAdapterStar(private val context: Context) : RecyclerView.Adapter<RecyclerAdapterStar.ViewHolder>() {
    var starlist = mutableListOf<StarData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.star_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = starlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(starlist[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val shopImg: ImageView = itemView.findViewById(R.id.star_imageView)
        private val name: TextView = itemView.findViewById(R.id.shopname2)
        private val address: TextView = itemView.findViewById(R.id.distance2)
        private val tel: TextView = itemView.findViewById(R.id.starscore2)

        fun bind(item: StarData) {
            Glide.with(itemView).load(item.img).into(shopImg)
            name.text = item.shop
            address.text = item.address
            tel.text = item.tel
        }
    }
}