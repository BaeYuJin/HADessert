package com.example.had

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityMainBinding
import com.example.had.databinding.ActivitySearchBinding
import com.example.had.databinding.DessertListBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = ArrayList<DataDessert>()
        list.add(DataDessert(null,"몽실", "3.7km", "4.9", null, "999+"))
        //list.add(DataDessert(getDrawable((R.drawable.~~)!!)""))
        list.add(DataDessert(null,"몽실", "3.7km", "4.9", null, "999+"))

        val adapter = RecyclerAdapterDessert(list)
        binding.dessertRv.adapter = adapter

    }
}