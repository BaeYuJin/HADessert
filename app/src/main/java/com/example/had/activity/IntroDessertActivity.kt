package com.example.had.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.had.R
import com.example.had.adapter.IntroDessertAdapter
import com.example.had.databinding.ActivityIntroDessertBinding
import com.example.had.dataclass.IntroDessertData

class IntroDessertActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroDessertBinding
    lateinit var introDessertAdapter: IntroDessertAdapter
    val datas = mutableListOf<IntroDessertData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroDessertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.introDessertRecyclerView.layoutManager = gridLayoutManager
    }

    private fun initRecycler() {
        introDessertAdapter = IntroDessertAdapter(this)
        binding.introDessertRecyclerView.adapter = introDessertAdapter

        datas.apply {
            add(IntroDessertData(img = R.drawable.macaron, name = "마카롱"))
            add(IntroDessertData(img = R.drawable.dacquoise, name = "다쿠아즈"))
            add(IntroDessertData(img = R.drawable.financier, name = "휘낭시에"))
            add(IntroDessertData(img = R.drawable.palmiercarre, name = "빨미까레"))
            add(IntroDessertData(img = R.drawable.eggtart, name = "에그타르트"))
            add(IntroDessertData(img = R.drawable.skon, name = "스콘"))
            add(IntroDessertData(img = R.drawable.madeleine, name = "마들렌"))
            add(IntroDessertData(img = R.drawable.montblanc, name = "몽블랑"))

            introDessertAdapter.datas = datas
            introDessertAdapter.notifyDataSetChanged()
        }
    }
}