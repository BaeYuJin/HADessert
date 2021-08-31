package com.example.had

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivitySearch1Binding

class Search1Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySearch1Binding
    val list = ArrayList<DataSearch>()
    val list2 = ArrayList<DataSearch>()
    private val adapter2 = RecyclerAdapterRecent(list2)

    var recentWord : String? = null

    //private val sList = PreferenceUtil.getRecentWords(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearch1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recentWord = query
                list2.add(DataSearch(recentWord!!))
                listRecent(list2)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.mainSearchView.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }


        list.add(DataSearch("마카롱"))
        list.add(DataSearch("치즈케이크"))
        val adapter = RecyclerAdapterPopular(list)
        binding.HotRv.adapter = adapter

        //Log.e("sList", sList.toString())
        binding.RecentRv.adapter = adapter2
    }

    fun listRecent(AddList : ArrayList<DataSearch>){
        Log.d("LIST2", AddList.toString())
        PreferenceUtil.setRecentWords(this, AddList)
        binding.RecentRv.adapter = adapter2
    }
}