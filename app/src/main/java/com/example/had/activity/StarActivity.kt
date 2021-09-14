package com.example.had.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.had.R
import com.example.had.adapter.IntroDessertAdapter
import com.example.had.adapter.RecyclerAdapterStar
import com.example.had.databinding.ActivityIntroDessertBinding
import com.example.had.databinding.ActivityMainBinding
import com.example.had.dataclass.DataStar
import com.example.had.dataclass.IntroDessertData

class StarActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var recyclerAdapterStar: RecyclerAdapterStar
    val starlist = mutableListOf<DataStar>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        initRecycler()

       /* val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.introDessertRecyclerView.layoutManager = gridLayoutManager*/
    }

    private fun initRecycler() {
        recyclerAdapterStar = RecyclerAdapterStar(this)
        binding.starRv.adapter = recyclerAdapterStar

        starlist.apply {
            add(DataStar(null, "이름", "주소","010"))

            recyclerAdapterStar.starlist = starlist
            recyclerAdapterStar.notifyDataSetChanged()
        }
    }
}