package com.example.had

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.had.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignupActivity : AppCompatActivity() {
    private val TAG = "SignUp"
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sAdapter = ArrayAdapter.createFromResource(this, R.array.catrgories, android.R.layout.simple_spinner_dropdown_item)
        binding.categoryComboBox.setAdapter(sAdapter);
        // Initialize Firebase Auth
        auth = Firebase.auth
        val database = Firebase.database
        val reference = database.getReference("Users")

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

            var name = binding.name.text.toString().trim()
            var pw = binding.pw.text.toString().trim()
            var email = binding.email.text.toString().trim()
            var emailad = binding.categoryComboBox.toString().trim() //db 추가해주세요
            email.plus(emailad)
            var birth = binding.birth.text.toString().trim()
            var sex : String = ""
            var uid : String = ""
            when (binding.radiogroup.checkedRadioButtonId) {
                R.id.male_rbt -> sex = "male".trim()
                R.id.female_rbt -> sex = "female".trim()
            }

            auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    val user = auth.currentUser
                    val hashMap: HashMap<Any, String> = HashMap()
                    user?.let {
                        uid = user.uid
                    }

                    hashMap.put("email", email)
                    hashMap.put("name", name)
                    hashMap.put("birth", birth)
                    hashMap.put("sex", sex)

                    reference.child(uid).setValue(hashMap)

                    Toast.makeText(baseContext, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "이미 등록된 이메일입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}