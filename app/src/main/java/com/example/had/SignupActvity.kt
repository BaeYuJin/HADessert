package com.example.had

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioGroup
import com.example.had.databinding.ActivitySignupBinding

class SignupActvity : AppCompatActivity() {
    val binding = ActivitySignupBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        binding.textView14.setVisibility(View.INVISIBLE)

        /*if (binding.pw.text != binding.pwcheck.text)
            binding.textView14.setVisibility(View.VISIBLE)*/

        binding.pwbtn.setOnClickListener {
            if (binding.pw.text != binding.pwcheck.text)
                binding.textView14.setVisibility(View.VISIBLE)
        }

        binding.signupbutton.setOnClickListener {

            var name = binding.name.text
            var id = binding.id.text
            var pw = binding.pw.text
            var email = binding.email.text
            var birth = binding.birth.text
            var sex : String
            when (binding.radiogroup.checkedRadioButtonId) {
                R.id.male_rbt -> sex = "남성"
                R.id.female_rbt -> sex = "여성"
            }

            Log.d("Name : ", name.toString())
        }
    }

}