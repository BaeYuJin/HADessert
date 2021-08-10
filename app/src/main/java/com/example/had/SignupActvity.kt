package com.example.had

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioGroup
import com.example.had.databinding.ActivitySignupBinding

class SignupActvity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_signup)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pwbtn.setOnClickListener {
            if (binding.pw.text.toString() != binding.pwcheck.text.toString()) {
                binding.textView14.setVisibility(View.VISIBLE)
                binding.textView.setVisibility(View.INVISIBLE)
            }
            else {
                binding.textView14.setVisibility(View.INVISIBLE)
                binding.textView.setVisibility(View.VISIBLE)
            }
            /*if (binding.pw.text.equals(binding.pwcheck.text))
                binding.textView14.setVisibility(View.INVISIBLE)
            else
                binding.textView14.setVisibility(View.VISIBLE)*/
            Log.d("pw : ", binding.pw.text.toString())
            Log.d("pwcheck : ", binding.pwcheck.text.toString())

        }

        binding.signupbutton.setOnClickListener {

            var name = binding.name.text
            var id = binding.id.text
            var pw = binding.pw.text
            var email = binding.email.text
            var birth = binding.birth.text
            var sex : String = ""
            when (binding.radiogroup.checkedRadioButtonId) {
                R.id.male_rbt -> sex = "남성"
                R.id.female_rbt -> sex = "여성"
            }

            //Log.d("Name : ", name.toString())
            Log.d("sex : ", sex)

            startActivity(Intent(this, LoginActivity::class.java))

        }
    }

}