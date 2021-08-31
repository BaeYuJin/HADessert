package com.example.had.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.had.PreferenceUtil
import com.example.had.adapter.RecyclerAdapterPopular
import com.example.had.adapter.RecyclerAdapterRecent
import com.example.had.databinding.ActivitySearch1Binding
import com.example.had.databinding.ActivitySearchBinding
import com.example.had.dataclass.DataSearch

class Search1Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySearch1Binding
    private lateinit var binding2: ActivitySearchBinding
    val list = ArrayList<DataSearch>()
    val list2 = ArrayList<DataSearch>()
    private val adapter2 = RecyclerAdapterRecent(list2)

    var recentWord : String? = null

    //private val sList = PreferenceUtil.getRecentWords(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearch1Binding.inflate(layoutInflater)
        binding2 = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = Intent(this, SearchActivity::class.java)

        binding.mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recentWord = query
                list2.add(DataSearch(recentWord!!))
                listRecent(list2)
                /*var intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("searchword", binding.mainSearchView.toString())*/
                //var query2 = binding.mainSearchView.query.toString()
                Log.d("query:", query)
                intent.putExtra("word", query)
                //binding2.textView4.text = query
                startActivity(intent)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.mainSearchView.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        var query = binding.mainSearchView.query
        //binding.mainSearchView.setQuery(binding.mainSearchView.query, true)


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

