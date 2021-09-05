package com.example.had.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.had.dataclass.DataDessert
import com.example.had.databinding.DessertListBinding


class RecyclerAdapterDessert(private val items: MutableList<DataDessert>) : RecyclerView.Adapter<RecyclerAdapterDessert.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DessertListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
            Log.d("ON CLICK", item.toString())
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
            //binding.heart.setImageDrawable(item.heart)
            binding.heartcnt.text = item.heartcnt

            binding.blankheart.setOnClickListener {
                binding.heart.setVisibility(View.VISIBLE)
                binding.blankheart.setVisibility(View.INVISIBLE)
            }
            binding.heart.setOnClickListener {
                binding.blankheart.setVisibility(View.VISIBLE)
                binding.heart.setVisibility(View.INVISIBLE)
            }

        }
    }
}