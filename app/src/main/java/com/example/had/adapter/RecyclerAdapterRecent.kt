package com.example.had.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.had.PreferenceUtil
import com.example.had.activity.Search1Activity
import com.example.had.activity.SearchActivity
import com.example.had.databinding.ActivitySearch1Binding
import com.example.had.dataclass.DataSearch
import com.example.had.databinding.RecentListBinding
import java.security.AccessController.getContext


class RecyclerAdapterRecent(private val items: MutableList<DataSearch>) : RecyclerView.Adapter<RecyclerAdapterRecent.ViewHolder>() {
    private lateinit var binding1: ActivitySearch1Binding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding1 = ActivitySearch1Binding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
            Log.d("ON CLICK", item.toString())
        }
        holder.apply {
            var intent = Intent(holder.itemView?.context, SearchActivity::class.java)
            bind(listener, item, position, intent)
            itemView.tag = item
        }

    }

    fun removeItem(position: Int){
        items.removeAt(position)
        PreferenceUtil.setRecentWords(binding1.imageView6.context, "WORD", items)
        notifyDataSetChanged()
    }

    fun setSearchText(position: Int){
        binding1.mainSearchView.setQuery(items[position].word, true)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: RecentListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item : DataSearch, position: Int, intent: Intent){
            binding.textView11.text = item.word

           binding.imageView2.setOnClickListener {
               removeItem(position)
               Log.d("position", position.toString())
           }
            binding.textView11.setOnClickListener {
                var query = binding.textView11.text.toString()
                //binding1.mainSearchView.setQuery(binding.textView11.text.toString(),true)
                //binding1.mainSearchView.setQuery(items[position].word,true)
                Log.d("query", binding1.mainSearchView.query.toString())
                intent.putExtra("word", query)
                ContextCompat.startActivity(binding.textView11.context, intent, null)
            }


            /*binding.textView11.setOnClickListener {
                setSearchText(position)
                Log.d("position", items[position].word)
            }*/

        }
    }
}
