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

        val list = ArrayList<DataDessert>()

        binding.textView4.text = intent.getStringExtra("word")


        val thread = Thread {
            var apiExamSearchBlog = NaverSearchPlace()
            apiExamSearchBlog.main(intent.getStringExtra("word").toString(), list)
        }.start()

        //list.add(DataDessert(null,"몽실", "3.7km", "4.9", null, "999+"))
        //list.add(DataDessert(getDrawable((R.drawable.~~)!!)""))
        //list.add(DataDessert(null,"몽실", "3.7km", "4.9", null, "999+"))
        //list.add(DataDessert(null,"몽실", "3.7km", "4.9", null, "999+"))

        val adapter = RecyclerAdapterDessert(list)
        binding.dessertRv.adapter = adapter



    }
}
