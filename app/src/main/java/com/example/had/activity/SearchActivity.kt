package com.example.had.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.had.adapter.RecyclerAdapterDessert
import com.example.had.databinding.ActivitySearchBinding
import com.example.had.dataclass.DataDessert
import com.example.test.NaverSearchPlace

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainSearchView2.setOnClickListener {
            finish()
        }

        val list = mutableListOf<DataDessert>()

        binding.textView4.text = intent.getStringExtra("word")

        val thread = Thread {
            var apiExamSearchBlog = NaverSearchPlace()
            apiExamSearchBlog.main(intent.getStringExtra("word").toString(), list)
        }.start()

        val adapter = RecyclerAdapterDessert(list)
        binding.dessertRv.adapter = adapter

    }
}
