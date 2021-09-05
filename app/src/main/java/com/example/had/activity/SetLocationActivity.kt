package com.example.had.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.example.had.databinding.ActivitySetLocationBinding

class SetLocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var setLocationEditText = binding.setLocationSearchEditText
        var findingLocationButton = binding.closeButtonTextView

        binding.setLocationBackButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        setLocationEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (setLocationEditText.toString().isNotBlank()) {
                    findingLocationButton.isVisible = true
                    findingLocationButton.setOnClickListener {
                        setLocationEditText.setText("")
                        findingLocationButton.isVisible = false
                    }
                }
                if (setLocationEditText.length() == 0)
                    findingLocationButton.isVisible = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}