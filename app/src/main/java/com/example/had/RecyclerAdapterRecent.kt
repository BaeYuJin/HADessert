package com.example.had

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.had.databinding.RecentListBinding


class RecyclerAdapterRecent(private val items: ArrayList<DataSearch>) : RecyclerView.Adapter<RecyclerAdapterRecent.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterRecent.ViewHolder {

        val binding = RecentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterRecent.ViewHolder, position: Int) {
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

    class ViewHolder(private val binding: RecentListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item : DataSearch){
            binding.textView11.text = item.word
        }
    }
}
