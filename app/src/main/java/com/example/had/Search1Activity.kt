package com.example.had

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivitySearch1Binding

class Search1Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySearch1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearch1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        /*binding.mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }

        })*/
        binding.mainSearchView.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        val list = ArrayList<DataSearch>()
        list.add(DataSearch("마카롱"))
        list.add(DataSearch("치즈케이크"))

        val list2 = ArrayList<DataSearch>()
        list2.add(DataSearch("마카롱맛집"))
        list2.add(DataSearch("치케맛집"))

        val adapter = RecyclerAdapterPopular(list)
        binding.HotRv.adapter = adapter

        val adapter2 = RecyclerAdapterRecent(list2)
        binding.RecentRv.adapter = adapter2

    }
}