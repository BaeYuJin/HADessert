package com.example.had.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.had.dataclass.IntroDessertData
import com.example.had.R

class IntroDessertAdapter(private val context: Context) : RecyclerView.Adapter<IntroDessertAdapter.ViewHolder>() {
    var datas = mutableListOf<IntroDessertData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.intro_dessert_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dessertName: TextView = itemView.findViewById(R.id.introDessertName_textView)
        private val dessertImg: ImageView = itemView.findViewById(R.id.intro_dessert_imageView)

        fun bind(item: IntroDessertData) {
            dessertName.text = item.name
            Glide.with(itemView).load(item.img).into(dessertImg)
        }
    }
}