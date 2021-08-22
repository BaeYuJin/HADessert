package com.example.had

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.had.databinding.DessertListBinding


class RecyclerAdapterDessert(private val items:ArrayList<DataDessert>) : RecyclerView.Adapter<RecyclerAdapterDessert.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterDessert.ViewHolder {

        val binding = DessertListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterDessert.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: DessertListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item : DataDessert){
            binding.image1.setImageDrawable(item.img)
            binding.shopname.text = item.name
            binding.distance.text = item.distance
            binding.starscore.text = item.star
            binding.heartcnt.text = item.heartcnt
        }
    }
}