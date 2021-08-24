package com.example.had

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityIntroDessertBinding

class IntroDessertActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroDessertBinding
    lateinit var introDessertAdapter: IntroDessertAdapter
    val datas = mutableListOf<IntroDessertData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroDessertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()
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

            introDessertAdapter.datas = datas
            introDessertAdapter.notifyDataSetChanged()
        }
    }
}