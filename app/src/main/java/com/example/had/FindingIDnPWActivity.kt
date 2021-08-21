package com.example.had

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivityFindingidpwBinding

class FindingIDnPWActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindingidpwBinding
    private var emailPattern = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindingidpwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.squareView.setOnClickListener {
            if(binding.findingEditTextEmailAddress.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex()))
                startActivity(Intent(this, ChangingIDnPWActivity::class.java))
            else {
                if (TextUtils.isEmpty(binding.findingEditTextEmailAddress.text)) {
                    Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this, "이메일 형식으로 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.phoneImageView.setOnClickListener {
            if(binding.findingEditTextEmailAddress.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex()))
                startActivity(Intent(this, ChangingIDnPWActivity::class.java))
            else {
                if (TextUtils.isEmpty(binding.findingEditTextEmailAddress.text)) {
                    Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this, "이메일 형식으로 입력하세요.", Toast.LENGTH_SHORT).show()
            }

        }
        binding.phoneTitleTextView.setOnClickListener {
            if(binding.findingEditTextEmailAddress.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex()))
                startActivity(Intent(this, ChangingIDnPWActivity::class.java))
            else {
                if (TextUtils.isEmpty(binding.findingEditTextEmailAddress.text)) {
                    Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this, "이메일 형식으로 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.phoneGuideTextView.setOnClickListener {
            if(binding.findingEditTextEmailAddress.text.toString().trim { it <= ' ' }.matches(emailPattern.toRegex()))
                startActivity(Intent(this, ChangingIDnPWActivity::class.java))
            else {
                if (TextUtils.isEmpty(binding.findingEditTextEmailAddress.text)) {
                    Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this, "이메일 형식으로 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}