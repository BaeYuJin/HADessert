package com.example.had.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.had.PreferenceUtil
import com.example.had.PreferenceUtil.getRecentWords
import com.example.had.adapter.RecyclerAdapterPopular
import com.example.had.adapter.RecyclerAdapterRecent
import com.example.had.databinding.ActivitySearch1Binding
import com.example.had.databinding.ActivitySearchBinding
import com.example.had.databinding.RecentListBinding
import com.example.had.dataclass.DataSearch

class Search1Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySearch1Binding
    private lateinit var binding2: ActivitySearchBinding
    private lateinit var binding3: RecentListBinding
    val list = mutableListOf<DataSearch>()
    var list2 = mutableListOf<DataSearch>()

    private val adapter = RecyclerAdapterPopular(list)
    private val adapter2 = RecyclerAdapterRecent(list2)

    var recentWord : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearch1Binding.inflate(layoutInflater)
        binding2 = ActivitySearchBinding.inflate(layoutInflater)
        binding3 = RecentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getlistRecent(list2) // 이 친구 안되면 그냥 주석처리해주세욥

        var intent = Intent(this, SearchActivity::class.java)

        binding.mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recentWord = query

                Log.d("list2:", list2.count().toString())

                if(list2.count() == 0){
                    list2.add(DataSearch(recentWord!!))
                    listRecent(list2)
                    // Log.d("query:", query)
                    intent.putExtra("word", query)
                    startActivity(intent)
                }
                else{
                    /* for (i in 0..list2.count()) {
                        /*if(query == list2[i].word){
                            // Log.d(" 일치함 :", list2[i].word)
                            continue
                        }*/
                        Log.d("list2[i] :", list2[i].word)
                    } */

                    Log.d("list2[i] :", list2.count().toString())

                    list2.add(DataSearch(recentWord!!))
                    listRecent(list2)
                    // Log.d("query:", query)
                    intent.putExtra("word", query)
                    startActivity(intent)
                }

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
        // val adapter = RecyclerAdapterPopular(list)
        //binding.HotRv.adapter = adapter

        //Log.e("sList", sList.toString())

        // val testList = getRecentWords(this, "WORD")
        //Log.d("testLIst", testList.toString())

        binding.RecentRv.adapter = adapter2
    }

    fun listRecent(AddList : MutableList<DataSearch>){
        Log.d("LIST2", AddList.toString())
        PreferenceUtil.setRecentWords(this, "WORD", AddList)
        binding.RecentRv.adapter = adapter2
    }

    fun getlistRecent(AddList: MutableList<DataSearch>){
        list2 = getRecentWords(this, "Word")
        binding.RecentRv.adapter = adapter2
    }
}

